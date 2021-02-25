package net.mhgoi.blog.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;
import org.postgresql.core.v3.QueryExecutorImpl;
import org.postgresql.jdbc.PgConnection;
import org.postgresql.jdbc.PgDatabaseMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.mhgoi.blog.components.SystemInfoUtil;
import net.mhgoi.blog.service.RuntimeService;

/*
 * @author 少年
 * @since 2021-01-19 18:18:27
 */
@Service("runtimeService")

public class RuntimeServiceImpl implements RuntimeService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RuntimeServiceImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SystemInfoUtil systemInfoUtil;



    public Map getThread() {
        Map map = new HashMap();

        Process process = null;
        SequenceInputStream sis = null;
        BufferedReader br = null;
        try {
            process = Runtime.getRuntime().exec("jinfo" + " " + (new ApplicationPid().toString()));
            process.waitFor();

            sis = new SequenceInputStream(process.getInputStream(), process.getErrorStream() );
            br = new BufferedReader(new InputStreamReader(sis, "utf-8"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = br.readLine()) != null) {
                result.append(line + "<br/>");
            }
            map.put("jinfo",result.toString());
        }catch(Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            IOUtils.closeQuietly(br, (Consumer<IOException>) null);
        }
        process.destroy();
        return map;
    }

    public Map getRuntime() {
        Map map = systemInfoUtil.getInfo();
        Map jdbcInfo = new HashMap();

        Connection conn = null;
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            DatabaseMetaData meta = conn.getMetaData();
            jdbcInfo.put("databaseVersion", meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion());
            jdbcInfo.put("url", meta.getURL());
            jdbcInfo.put("driverName", meta.getDriverName());
            jdbcInfo.put("driverVersion", meta.getDriverVersion());

            if (meta instanceof PgDatabaseMetaData) {
                //兼容cockroach
                Map parameterStatuses = ((QueryExecutorImpl) ((PgConnection) ((PgDatabaseMetaData)meta).getConnection()).getQueryExecutor()).getParameterStatuses();
                if (parameterStatuses.containsKey("crdb_version")) {
                    jdbcInfo.put("databaseVersion", parameterStatuses.get("crdb_version"));
                }
            }


        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
            map.put("jdbcInfo", jdbcInfo);
        }

        return map;
    }
}
