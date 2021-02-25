package net.mhgoi.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.mhgoi.blog.entity.Notice;
import net.mhgoi.blog.mapper.NoticeMapper;
import net.mhgoi.blog.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * (Notice)表服务实现类
 *
 * @author 少年
 * @since 2020-08-14 16:55:05
 */
@Service("noticeService")
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

}