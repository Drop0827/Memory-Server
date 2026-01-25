package ohh.net.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ohh.net.model.Assistant;
import ohh.net.web.mapper.AssistantMapper;
import ohh.net.web.service.AssistantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssistantServiceImpl extends ServiceImpl<AssistantMapper, Assistant> implements AssistantService {

}