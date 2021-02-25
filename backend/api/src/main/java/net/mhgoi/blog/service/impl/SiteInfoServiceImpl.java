package net.mhgoi.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.mhgoi.blog.entity.SiteInfo;
import net.mhgoi.blog.mapper.SiteInfoMapper;
import net.mhgoi.blog.service.SiteInfoService;
import org.springframework.stereotype.Service;

/**
 * (SiteInfo)表服务实现类
 *
 * @author 少年
 * @since 2020-07-31 07:50:23
 */
@Service("siteInfoService")
public class SiteInfoServiceImpl extends ServiceImpl<SiteInfoMapper, SiteInfo> implements SiteInfoService {

}