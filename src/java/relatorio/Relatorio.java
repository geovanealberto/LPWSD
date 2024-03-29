/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;

/**
 *
 * @author alunoces
 */
public class Relatorio 
{
    private HttpServletResponse response;
    private FacesContext context;
    private ByteArrayOutputStream baos;
    private InputStream stream;
    private Connection cnx; 
    
    public Relatorio() {
        this.context = FacesContext.getCurrentInstance();
        this.response = (HttpServletResponse) context.getExternalContext().getResponse();
    }
   
    public void getRelatorio()
    {
        stream = this.getClass().getResourceAsStream("relatorioassuntos.jasper");
        Map<String, Object> params = new HashMap<String, Object>(); 
        baos = new ByteArrayOutputStream();
    
        try
        {
            JasperReport report  = (JasperReport) JRLoader.loadObject(stream);
            
            //JasperPrint print = JasperFillManager.fillReport(report, params, getConexao());
            //JasperExportManager.exportReportToPdfStream(print, baos);
        
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline: filename=relatorio.pdf");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
            context.responseComplete();
            //fecharConexao();
        }
        catch (JRException ex)
        {
            //Logger.getLogger(Relatorio.class.getName()). log(Level.SEVERE, null, ex);
        }
        catch(IOException ex)
        {
           //Logger.getLogger(Relatorio.class.getName()). log(Level.SEVERE, null, ex);
        }
    }   
}
