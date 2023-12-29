package net.mhgoi.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import net.mhgoi.blog.dto.ArticleDto;
import net.mhgoi.blog.dto.PageResult;
import net.mhgoi.blog.entity.SiteInfo;
import net.mhgoi.blog.service.ArticleService;
import net.mhgoi.blog.service.SiteInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("feed")
@io.swagger.annotations.Api(value = "站点feed接口", tags = {"站点feed接口"})
public class FeedController {

    @Resource
    private ArticleService articleService;
    @Resource
    private SiteInfoService siteInfoService;

    @GetMapping("/rss")
    public void rss(HttpServletResponse response) throws IOException, FeedException {

        SiteInfo site = siteInfoService.getOne(null);
        Map map = new HashMap();
        Page<ArticleDto> page = articleService.queryPage(1, 30, map);
        PageResult<ArticleDto> pageResult = new PageResult<>(page.getTotal(), page.getRecords());

        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle(site.getName());
        feed.setGenerator(site.getDomain());
        feed.setDescription(site.getDescription());
        feed.setLink(site.getDomain());

        feed.setAuthor(site.getName());
        //feed.setEncoding("en");
        feed.setPublishedDate(new java.util.Date());
        List<SyndEntry> list = new ArrayList<SyndEntry>();
        for (ArticleDto a : pageResult.getRows()) {
            SyndEntry e = new SyndEntryImpl();
            String link = site.getDomain() + "/post/" + a.getSlug();
            e.setLink(link);
            e.setTitle(a.getTitle());
            e.setPublishedDate(a.getCreateTime());
            //e.setDescription(a.getSummary());
            list.add(e);
        }
        feed.setEntries(list);
        response.setContentType("application/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(new SyndFeedOutput().outputString(feed));
        out.flush();
        out.close();
    }

}
