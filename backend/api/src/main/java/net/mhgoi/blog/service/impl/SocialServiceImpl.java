package net.mhgoi.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.mhgoi.blog.entity.Social;
import net.mhgoi.blog.mapper.SocialMapper;
import net.mhgoi.blog.service.SocialService;
import org.springframework.stereotype.Service;

/**
 * (Social)表服务实现类
 *
 * @author 少年
 * @since 2020-07-29 13:25:36
 */
@Service("socialService")
public class SocialServiceImpl extends ServiceImpl<SocialMapper, Social> implements SocialService {

}