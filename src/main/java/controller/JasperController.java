package controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;


@Controller
@Slf4j
public class JasperController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/jasper/download")
	public void downloadFile(@RequestParam("type") String type, HttpServletResponse response) throws Exception {
		Connection conn = null;
		
		try 
		{
			// JDBC 드라이버 로드 및 연결 설정
			Class.forName("oracle.jdbc.OracleDriver");
			String ip = "localhost";
			String port = "1521"; // 포트를 1521로 변경
			String sid = "XE";
			String user = "JMK";
			String password = "JMK";
			
			// 한글처리
			response.setCharacterEncoding("UTF-8");
			
			// 1. 데이터베이스에 연결
			conn = DriverManager.getConnection(String.format("jdbc:oracle:thin:@%s:%s:%s", ip, port, sid), user, password);
            
			InputStream inputstream = this.getClass().getClassLoader().getResourceAsStream("/jasper/SampleReportDB.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(inputstream);
			
			// 보고서에 전달할 매개변수 설정
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("logo", "images/회사직인.png");
            
            // 보고서 채우기
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            
            // PDF로 변환 및 다운로드
            if(StringUtils.pathEquals(type, "pdf")) {
            	
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=test.pdf");
                OutputStream outputStream = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                
                outputStream.flush();
                outputStream.close();
            }
            
            // EXCEL로 변환 및 다운로드
            if(StringUtils.pathEquals(type, "excel")) {
            	
            	response.setContentType("application/vnd.ms-excel");
            	response.setHeader("Content-Disposition", "attachment; filename=test.xlsx");
            	OutputStream outputStream = response.getOutputStream();
            	JRXlsxExporter exporter = new JRXlsxExporter();
            	
            	SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            	configuration.setDetectCellType(true);
            	configuration.setCollapseRowSpan(false);
            	
            	exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            	exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            	exporter.setConfiguration(configuration);
            	exporter.exportReport();
            	
            	outputStream.flush();
            	outputStream.close();
            }
            
            // HTML로 변환 및 다운로드
            if(StringUtils.pathEquals(type, "html")) {
            	// 응답 설정
            	response.setContentType("text/html");
            	response.setHeader("Content-Disposition", "attachment; filename=SampleReportDB.html");

            	OutputStream outputStream = response.getOutputStream();
            	HtmlExporter exporter = new HtmlExporter();
            	exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            	exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
            	
                exporter.exportReport();
                
            	outputStream.flush();
            	outputStream.close();
            }
        	
			
		} catch (Exception e) {
			log.warn(e.toString());
		}finally {
			
			if(conn != null) {
				conn.close();
			}
		}
	}
}
