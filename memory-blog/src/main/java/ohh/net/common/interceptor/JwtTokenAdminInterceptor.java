package ohh.net.common.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ohh.net.common.annotation.NoTokenRequired;
import ohh.net.common.exception.CustomException;
import ohh.net.common.properties.JwtProperties;
import ohh.net.common.utils.BlackListUtils;
import ohh.net.common.utils.IpUtils;
import ohh.net.common.utils.JwtUtils;
import ohh.net.model.UserToken;
import ohh.net.web.mapper.UserTokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private UserTokenMapper userTokenMapper;
    @Resource
    private BlackListUtils blackListUtils;

    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response,
            @NotNull Object handler) {
        // 从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenName());

        // 获取客户端IP
        String ip = IpUtils.getRealIp(request);

        // 检查IP是否在黑名单中
        if (blackListUtils.isBlacklisted(ip)) {
            log.warn("IP {} 已被加入黑名单", ip);
            throw new CustomException("您已被加入黑名单，请稍后再试");
        }

        // 如果是预检请求，直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 判断是否为 HandlerMethod（Controller 方法）
        // 如果不是（例如静态资源请求），直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 检查方法上是否有@NoTokenRequired注解，如果有就直接放行
        if (method.isAnnotationPresent(NoTokenRequired.class)) {
            return true;
        }

        // 校验令牌
        try {
            log.info("jwt校验：{}", token);

            // 如果是GET请求没有传token就直接放行，传了token就必须经过验证
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                if (token != null) {
                    if (token.startsWith("Bearer "))
                        token = token.substring(7);
                    JwtUtils.parseJWT(token);
                }
                return true;
            }

            // 校验令牌是否存在（排除GET请求且无Token的情况）
            if (token == null) {
                // 如果是GET请求（上面已经处理过有token的情况，这里是无token情况），且未加@NoTokenRequired（如果有注解在L68已放行）
                // Wait, L77 says: If GET, if token!=null verify, else return true.
                // So if GET and token==null, it returns true at L83.
                // So execution only reaches here if NOT GET, or (GET and token!=null which is
                // handled).
                // Actually L77 returns true. So L86 is reachable only if NOT GET.
                // If NOT GET and token is null -> we should throw error.
                throw new CustomException(401, "未登录");
            }

            // 如果没有Token，直接抛出未登录异常
            if (token == null || token.isEmpty()) {
                throw new CustomException(401, "未登录，请先登录");
            }

            // 处理Authorization的Bearer
            if (token.startsWith("Bearer "))
                token = token.substring(7);

            LambdaQueryWrapper<UserToken> userTokenLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userTokenLambdaQueryWrapper.eq(UserToken::getToken, token);
            List<UserToken> userTokens = userTokenMapper.selectList(userTokenLambdaQueryWrapper);

            // 如果跟之前的token相匹配则进一步判断token是否有效
            if (userTokens != null && !userTokens.isEmpty()) {
                JwtUtils.parseJWT(token);
                return true;
            } else {
                throw new CustomException(401, "该账号已在另一台设备登录");
            }
        } catch (Exception ex) {
            System.out.println("校验失败：" + ex);
            // 校验失败，响应401状态码
            response.setStatus(401);
            String message = ex.getMessage() != null ? ex.getMessage() : "无效或过期的token";
            throw new CustomException(401, message);
        }
    }
}