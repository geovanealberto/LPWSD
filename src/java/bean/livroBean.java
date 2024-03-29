/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import Modelo.TbLivro;
import java.util.ArrayList;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import dao.LivroDAO;
import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;


@ManagedBean
@ViewScoped
public class livroBean {

    TbLivro livro = new TbLivro();

    List livros = new ArrayList();

    //construtor
    public livroBean() {
        livros = new LivroDAO().buscarTodas();
        livro = new TbLivro();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        new LivroDAO().persistir(livro);
        livros = new LivroDAO().buscarTodas();
        livro = new TbLivro();
    }

    public void exclude(ActionEvent actionEvent) {
        new LivroDAO().remover(livro);
        livros = new LivroDAO().buscarTodas();
        livro = new TbLivro();
    }

    //getters and setters
    public TbLivro getLivro() {
        return livro;
    }

    public void setLivro(TbLivro livro) {
        this.livro = livro;
    }

    public List getLivros() {
        return livros;
    }

    public void setLivros(List livros) {
        this.livros = livros;
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

    }

}
