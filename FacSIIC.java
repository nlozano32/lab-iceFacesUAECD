package co.gov.uaecd.siic.facade;

import co.com.sogamoxi.auditoria.entities.AplException;

import co.gov.uaecd.siic.entities.Avaluos;
import co.gov.uaecd.siic.entities.Barrio;
import co.gov.uaecd.siic.entities.Boletines;
import co.gov.uaecd.siic.entities.CaratulaMutacionWeb;
import co.gov.uaecd.siic.entities.CertificadosVivienda;
import co.gov.uaecd.siic.entities.Destino;
import co.gov.uaecd.siic.entities.DestinosHacendario;
import co.gov.uaecd.siic.entities.DireccionSecundaria;
import co.gov.uaecd.siic.entities.HistoricoIdentificador;
import co.gov.uaecd.siic.entities.Parametros;
import co.gov.uaecd.siic.entities.PasoRadicacion;
import co.gov.uaecd.siic.entities.PredioIdentificador;
import co.gov.uaecd.siic.entities.PredioMutacionWeb;
import co.gov.uaecd.siic.entities.PropietarioMutacionWeb;
import co.gov.uaecd.siic.entities.PropietarioPredio;
import co.gov.uaecd.siic.entities.Radicacion;
import co.gov.uaecd.siic.entities.RadicacionMutacion;
import co.gov.uaecd.siic.entities.Usuario;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfEncryptor;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;


@SuppressWarnings("unchecked")
public class FacSIIC {
    private EntityManager em = null;
    private AplException apl = new AplException();
    private int codUsuarioCon;
    private String ipRemoto = "";
    private Usuario usuario;

    com.lowagie.text.Font font = FontFactory.getFont("Verdana", 11F, 1, new Color(0, 0, 0));
    com.lowagie.text.Font font1 = FontFactory.getFont("Verdana", 7F, 1, new Color(0, 0, 0));
    com.lowagie.text.Font font2 = FontFactory.getFont("Verdana", 10F, 1, new Color(0, 0, 0));
    com.lowagie.text.Font font3 = FontFactory.getFont("Times New Roman", 10F, 1, new Color(0, 0, 0));
    com.lowagie.text.Font font4 = FontFactory.getFont("Verdana", 9F, 1, new Color(0, 0, 0));
    com.lowagie.text.Font font5 = FontFactory.getFont("Verdana", 6F, 1, new Color(0, 0, 0));

    public FacSIIC() {
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("SIIC");
        //em = emf.createEntityManager();
    }

    private void checkEntityManager() {
        if (em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Misional");
            em = emf.createEntityManager();
            em.setFlushMode(FlushModeType.AUTO);
        }
        else {
            if (!em.isOpen()) {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("Misional");
                em = emf.createEntityManager();
                em.setFlushMode(FlushModeType.AUTO);
            }
        }
    }

    private void closeEM() {
        if (em.isOpen())
            em.close();
    }

    public PredioIdentificador gtPredio(String chip) {
        PredioIdentificador predio;
        try {
            checkEntityManager();
            em.clear();
            predio = (PredioIdentificador)em.createNamedQuery("PredioIdentificador.getPredioSNChip").setParameter("parChip", chip).getSingleResult();
        }
        catch (Exception ex) {
            return null;
        }
        finally {
            closeEM();
        }
        return predio;
    }

    public PredioIdentificador gtPredio(String codBarrio, String codManzana, String codPredio, String codConstruccion, String codResto) {
        PredioIdentificador predio;
        try {
            checkEntityManager();
            em.clear();
            predio = (PredioIdentificador)em.createNamedQuery("PredioIdentificador.getPredioCS").setParameter("parCodBarrio", codBarrio).setParameter("parCodManzana", codManzana).setParameter("parCodPredio", codPredio).setParameter("parCodConstruccion", codConstruccion).setParameter("parCodResto", codResto).getSingleResult();
        }
        catch (Exception ex) {
            return null;
        }
        finally {
            closeEM();
        }
        return predio;
    }

 
    public Boletines isBoletin(String chip) {
        checkEntityManager();
        em.clear();
        Boletines b = null;
        List<Vector> vectores;
        try {
            vectores = em.createNativeQuery("SELECT cod_boletin FROM boletines WHERE TO_DATE(fecha_generacion, " + "'yyyy-mm-dd') = (SELECT TO_DATE(SYSDATE, 'yyyy-mm-dd') FROM DUAL) AND " + "SUBSTR(nombre_fisico, 1, 11) = '" + chip + "'").getResultList();
            for (Vector vector: vectores) {
                b = em.find(Boletines.class, Long.parseLong(vector.get(0).toString()));
                break;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            closeEM();
        }
        return b;
    }

    public Boletines getBoletin(Long codigo) {
        checkEntityManager();
        em.clear();
        Boletines b = em.find(Boletines.class, codigo);
        closeEM();
        return b;
    }

    public Boletines isBoletin(String chip, int codUsuario) {
        checkEntityManager();
        em.clear();
        Boletines b = null;
        try {
            List<Vector> vectores = em.createNativeQuery("SELECT cod_boletin FROM boletines WHERE TO_DATE(fecha_generacion, " + "'yyyy-mm-dd') = (SELECT TO_DATE(SYSDATE, 'yyyy-mm-dd') FROM DUAL) AND " + "SUBSTR(nombre_fisico, 1, 11) = '" + chip + "' AND usu_codigo = " + codUsuario).getResultList();
            for (Vector vector: vectores) {
                b = em.find(Boletines.class, Long.parseLong(vector.get(0).toString()));
                break;
            }
        }
        catch (Exception ex) {
            System.out.println("NO SE ENCONTRO BOLETIN GENERADO ......!");
            //ex.printStackTrace();
        }
        finally {
            closeEM();
        }
        return b;
    }

    public CertificadosVivienda isCertificado(String identificacion, int codUsuario) {
        checkEntityManager();
        em.clear();
        CertificadosVivienda b = null;
        try {
            List<Vector> vectores = em.createNativeQuery("SELECT * FROM certificadosvivienda WHERE IDENTIFICACION_PROPIETARIO = '" + identificacion + "' AND TO_DATE(FECHA_GENERACION, 'yyyy-mm-dd') = (SELECT TO_DATE(SYSDATE, 'yyyy-mm-dd') FROM DUAL) AND usu_codigo = " + codUsuario).getResultList();
            for (Vector vector: vectores) {
                b = em.find(CertificadosVivienda.class, Long.parseLong(vector.get(0).toString()));
                break;
            }
        }
        catch (Exception ex) {
            System.out.println("NO SE ENCONTRO BOLETIN GENERADO ......!");
            //ex.printStackTrace();
        }
        finally {
            closeEM();
        }
        return b;
    }

    public File addCertificadoV(CertificadosVivienda certificado, List<PropietarioPredio> propietarios) {
        long codCertificado = Long.parseLong(getNumRadicacionWeb("CERT", true).toString());
        certificado.setNumeroRadicacion(getNumRadicacionWeb("RADICACION", true));
        certificado.setCodCertificado(codCertificado);
        certificado.setNumeroCertificado(codCertificado + "" + digitoVerificacion(codCertificado + ""));
        certificado.setUsuario(getUsuario(codUsuarioCon));
        checkEntityManager();
        EntityTransaction tx = em.getTransaction();
        File file;
        try {
            tx.begin();
            Date fecha = new Date();
            certificado.setNombreFisico((obtenerFechaSistema(fecha).replace(":", "")).replace(" ", "_") + "_" + certificado.getIdentificacionPropietario());
            certificado.setFechaGeneracion(new java.sql.Timestamp(fecha.getTime()));
            certificado.setIpremota(getIpRemoto());
            ResourceBundle rb = ResourceBundle.getBundle("co.gov.uaecd.siic.facade.propiedades");
            file = reporteCertificado(propietarios, certificado, rb.getString("carpetacertificados"), rb.getString("imagenes"), obtenerFechaSistema(fecha), certificado.getNombreFisico());
            certificado.setImagenPdf(getBytesFromFile(file));
            em.persist(certificado);
            tx.commit();
            apl.addUpdate(this.getClass().getPackage().getName(), "Certificado generado para el ciudadano : " + certificado.getIdentificacionPropietario(), "addCertificadoV(CertificadosVivienda certificado, List<PropietarioPredio> propietarios)", "CERTIFICADOSVIVIENDA", ipRemoto, codUsuarioCon);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            tx = null;
            closeEM();
        }

        return file;
    }

    public PredioIdentificador getPredioCS(String[] parcodSector) {
        checkEntityManager();
        em.clear();
        try {
            return (PredioIdentificador)getQuery("getPredioCS", getParametrosCS(), parcodSector).getSingleResult();
        }
        catch (Exception ex) {
            System.out.println("No existe predio para los parametros dados --> :  " + parcodSector[0] + " " + parcodSector[1] + " " + parcodSector[2] + " " + parcodSector[3] + " " + parcodSector[4] + " ");
            System.out.println("msjError -------> [" + ex.getMessage() + "]");
            return null;
        }
        finally {
            closeEM();
        }
    }

    private Query getQuery(String name, String[] nameParametros, String[] parametros) {
        Query query = em.createNamedQuery(name);
        for (int i = 0; i < nameParametros.length; i++) {
            query = query.setParameter(nameParametros[i], parametros[i]);
        }

        return query;
    }

    private String[] getParametrosCS() {
        return new String[] { "parCodBarrio", "parCodManzana", "parCodPredio", "parCodConstruccion", "parCodResto" };
    }

    public List<PredioIdentificador> getPredios(String codBarrio, String codManzana) {
        List<PredioIdentificador> predios = new ArrayList<PredioIdentificador>();
        checkEntityManager();
        em.clear();
        predios = em.createNamedQuery("getPredios").setParameter("parCodBarrio", codBarrio).setParameter("parCodManzana", codManzana).getResultList();
        closeEM();
        if (predios != null && predios.size() <= 0)
            predios = null;
        return predios;
    }

    private String obtenerFechaSistema(Date date) {
        String fecha = null;
        Locale formato = new Locale("es", "CO");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", formato);
        fecha = formatter.format(date);
        return fecha;
    }

    public File addBoletin(Boletines boletin) {
        long codBoletin = Long.parseLong(getNumRadicacionWeb("CONS", true).toString());
        boletin.setNumeroRadicacion(getNumRadicacionWeb("RADICACION", true));
        boletin.setUsuario(getUsuario(codUsuarioCon));
        checkEntityManager();
        EntityTransaction tx = em.getTransaction();
        File file;
        try {
            tx.begin();
            Date fecha = new Date();
            java.sql.Timestamp fechaSQL = new java.sql.Timestamp(fecha.getTime());
            boletin.setChip(boletin.getPredio().getChip());
            boletin.setFechaGeneracion(new java.sql.Timestamp(fecha.getTime()));
            boletin.setNombreFisico(boletin.getChip() + "_" + (fechaSQL.toString().substring(0, 19)).replace(":", "_"));
            boletin.setNombreFisico(boletin.getNombreFisico().replace(" ", "_"));
            boletin.setCodigoBarrio(boletin.getPredio().getPk().getCodigoBarrio());
            boletin.setCodigoManzana(boletin.getPredio().getPk().getCodigoManzana());
            boletin.setCodigoPredio(boletin.getPredio().getPk().getCodigoPredio());
            boletin.setCodigoConstruccion(boletin.getPredio().getPk().getCodigoConstruccion());
            boletin.setCodigoResto(boletin.getPredio().getPk().getCodigoResto());
            boletin.setIpremota(getIpRemoto());
            boletin.setCodBoletin(codBoletin);

            boletin.setNumeroBoletin(codBoletin + "" + digitoVerificacion(codBoletin + ""));
            ResourceBundle rb = ResourceBundle.getBundle("co.gov.uaecd.siic.facade.propiedades");
            file = reporteBoletin(boletin, rb.getString("carpetaboletines"), boletin.getFechaGeneracion().toString(), boletin.getNombreFisico());

            boletin.setImagenPdf(getBytesFromFile(file));
            em.persist(boletin);
            tx.commit();
            apl.addUpdate(this.getClass().getPackage().getName(), "Boletín generado por el ciudadano para el predio: " + boletin.getChip(), "addBoletin(Boletines boletin)", "BOLETINES", ipRemoto, codUsuarioCon);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            tx = null;
            closeEM();
        }

        return file;
    }

    public File getFileFromBytes(byte[] aByte, String fileName) {
        File file = new File(fileName);
        FileOutputStream fileO;
        try {
            fileO = new FileOutputStream(file);
            byte[] arc = aByte;
            fileO.write(arc);
            fileO.flush();
            fileO.close();
            file.deleteOnExit();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File getFileFrmBytes(byte[] aByte, String fileName) {
        File fileEx = null;
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            byte[] buffer = new byte[10];
            int nbytes = 0;
            ByteArrayInputStream bais = new ByteArrayInputStream(aByte);
            while ((nbytes = bais.read(buffer)) != -1)
                file.write(buffer, 0, nbytes);
            file.flush();
            file.close();
            bais.close();
            fileEx = new File(fileName);
            fileEx.deleteOnExit();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileEx;
    }

    public byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        byte[] bytes = new byte[(int)length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("No se pudo convertir completamente el archivo " + file.getName());
        }
        is.close();
        return bytes;
    }

    private Table encabezadoCertificado2(String rutaImagenes, String fecha, Long numRadicacion) throws BadElementException, DocumentException, MalformedURLException, IOException {

        fecha = fecha.substring(0, 10);
        String fecArr[] = fecha.split("-");

        Table table = new Table(3);
        table.setBorderWidth(1);
        table.setPadding(2);
        table.setSpacing(2);
        table.setWidths(new int[] { 13, 72, 15 });
        table.setWidth(100f);

        Cell cellLogo = new Cell(Image.getInstance(rutaImagenes + "LogoCatastroCOLOR.jpg"));
        cellLogo.setVerticalAlignment("middle");
        cellLogo.setBorder(0);
        Phrase titulo = new Phrase(11F, "\nUNIDAD ADMINISTRATIVA ESPECIAL DE CATASTRO DISTRITAL\n" + "\nRadicación Nº W-" + numRadicacion.intValue() + "  Bogotá D.C., " + getNombreMes(Integer.parseInt(fecArr[1])) + " " + fecArr[2] + " del " + fecArr[0], FontFactory.getFont("Helvetica", 11F, 1, new Color(0, 0, 0)));
        Cell cellTitulo = new Cell(titulo);
        cellTitulo.setHorizontalAlignment(1);
        cellTitulo.setVerticalAlignment(1);
        cellTitulo.setBorder(0);

        Cell img3 = new Cell(Image.getInstance(rutaImagenes + "bogotaPositiva.jpg"));
        img3.setVerticalAlignment("middle");
        img3.setHorizontalAlignment("right");
        img3.setBorder(0);
        table.addCell(cellLogo);
        table.addCell(cellTitulo);
        table.addCell(img3);
        return table;
    }

    private Table numbCertificado(String numCerti) throws BadElementException, DocumentException {
        Table table = new Table(1);
        table.setBorderWidth(0);
        table.setPadding(2f);
        table.setSpacing(2f);
        table.setWidth(100f);
        Cell numBol = new Cell(new Phrase(10f, "No. " + numCerti, FontFactory.getFont("Helvetica", 10F, 1, new Color(0, 0, 0))));
        numBol.setMaxLines(10);
        numBol.setHorizontalAlignment("right");
        numBol.setVerticalAlignment(1);
        numBol.setBorder(0);
        table.addCell(numBol);
        return table;
    }

    private Table encabezadoCertificado(String rutaImagenes, String fecha, String numCerti) throws BadElementException, DocumentException, IOException {
        fecha = fecha.substring(0, 10);
        Table table = new Table(1);
        table.setBorderWidth(0f);
        table.setBorder(1);
        table.setPadding(2);
        table.setSpacing(2);
        table.setWidths(new int[] { 100 });
        table.setWidth(100f);
        Table tableLogo = new Table(3);
        tableLogo.setWidths(new int[] { 35, 30, 35 });
        Cell cellVacio = new Cell();
        cellVacio.setBorder(0);
        Cell cellLogo = new Cell(Image.getInstance(rutaImagenes + "LogoCatastroCOLOR.jpg"));
        cellLogo.setBorder(0);
        cellLogo.setHorizontalAlignment(1);
        cellLogo.setVerticalAlignment(1);
        tableLogo.addCell(cellVacio);
        tableLogo.addCell(cellLogo);
        tableLogo.addCell(cellVacio);
        Phrase subt1 = new Phrase(12F, "\n\nRESPONSABLE ÁREA SERVICIO AL USUARIO DE LA UAECD\n\n\nCERTIFICA : ", FontFactory.getFont("Helvetica", 12F, 1, new Color(0, 0, 0)));
        Cell cell1 = new Cell(subt1);
        cell1.setBorder(0);
        cell1.setHorizontalAlignment(1);
        cell1.setVerticalAlignment(1);
        Phrase subt2 = new Phrase(11F, "\n\nQue consultando el Sistema Integrado de Información Catastral S.I.I.C.\n\n\n", FontFactory.getFont("Helvetica", 11F, 0, new Color(0, 0, 0)));
        Cell cell2 = new Cell(subt2);
        cell2.setBorder(0);
        table.addCell(cell1);
        table.addCell(cell2);
        return table;
    }

    private Table cuerpoNombreCertificado(String nombre, String tipoId, String cc) {
        if (tipoId.equals("CC"))
            tipoId = "C.C.";
        else if (tipoId.equals("NIT"))
            tipoId = "NIT";
        else if (tipoId.equals("TI"))
            tipoId = "TARJ";
        else if (tipoId.equals("CE"))
            tipoId = "EXTR";
        Table table = null;
        try {
            table = new Table(1);
            table.setBorderWidth(0);
            table.setWidth(100f);
            table.setPadding(3);
            table.setSpacing(3);
            Cell c = new Cell(new Phrase(12f, nombre.toUpperCase() + ", " + tipoId + " " + cc, FontFactory.getFont("Helvetica", 12f, 1, new Color(0, 0, 0))));
            c.setBackgroundColor(new Color(0xDCDCDC));
            c.setHorizontalAlignment("center");
            c.setVerticalAlignment("center");
            table.addCell(c);
        }
        catch (BadElementException ex) {
            ex.printStackTrace();
        }
        return table;
    }


    private Cell cellFecCuerpoBoletin(String fecha, String razonSocialEmpresa) throws BadElementException {
        fecha = fecha.substring(0, 10);
        String fecArr[] = fecha.split("-");
        String mes = getNombreMes(Integer.parseInt(fecArr[1]));
        if (razonSocialEmpresa == null) {
            razonSocialEmpresa = "el ciudadano";
        }
        Cell c = new Cell(new Phrase(8f, "Generado via web a los " + fecArr[2] + " días del mes de " + mes + " de " + fecArr[0] + " por " + razonSocialEmpresa + ".", FontFactory.getFont("Helvetica", 8f, 0, new Color(0, 0, 0))));
        c.setBorder(0);
        c.setBorderWidth(0);
        return c;
    }

    private Cell cellCuerpoBoletin(String txt, String horizAlign, String vertiAlign, float tam) throws BadElementException {
        Cell c = new Cell(new Phrase(tam, txt, FontFactory.getFont("Helvetica", tam, 0, new Color(0, 0, 0))));
        c.setBorder(0);
        c.setBorderWidth(0);
        c.setHorizontalAlignment(horizAlign);
        c.setVerticalAlignment(vertiAlign);
        return c;
    }

    private Cell firmaMecanicaCuerpoCertificado(String carpetaImagenes) throws BadElementException, DocumentException, MalformedURLException, IOException {
        Image img = Image.getInstance(carpetaImagenes + "firmaMIC.jpg");
        img.scaleAbsoluteWidth(130f);
        img.scaleAbsoluteHeight(25f);
        Cell c = new Cell(img);
        c.setBorder(0);
        c.setBorderWidth(0);
        c.setVerticalAlignment("middle");
        c.setHorizontalAlignment("center");
        return c;
    }

    private Cell cellCuerpoBoletin(String txt) throws BadElementException {
        Cell c = new Cell(new Phrase(9f, txt, FontFactory.getFont("Helvetica", 9f, 0, new Color(0, 0, 0))));
        c.setBorder(0);
        c.setBorderWidth(0);
        return c;
    }

    private Table cuerpotextoCertificado(List<PropietarioPredio> propietarios, String fecha, String razonSocialEmpresa, String carpetaBoletines) throws BadElementException, MalformedURLException, IOException, DocumentException {
        Table table = new Table(1);
        table.setBorderWidth(0);
        table.setBorder(1);
        table.setWidth(100f);
        table.setPadding(2);
        table.setSpacing(2);
        //table.setBackgroundColor(new Color(0xFFFFFF,0xFFFFFF,0xFFFFFF));
        String str1 = null;
        str1 = "No se encontró inscrito en el archivo magnético de la U.A.E.C.D. como propietario(a) de bienes inmuebles en el Distrito Capital.";
        if (propietarios != null && propietarios.size() > 0) {
            str1 = "Se encontró inscrito en el archivo magnético de la U.A.E.C.D. como propietario(a) de bienes inmuebles en el Distrito Capital." + "\n\n\nAdjunto ";
            if (propietarios.size() == 1)
                str1 = str1 + " (1) boletín con la información correspondiente.";
            else
                str1 = str1 + "(" + propietarios.size() + ") boletines con la información correspondiente.";
        }

        Cell c = new Cell(new Phrase(11f, str1, FontFactory.getFont("Helvetica", 11f, 0, new Color(0, 0, 0))));
        c.setBorder(0);
        c.setHorizontalAlignment("justify");
        table.addCell(c);

        Cell c1 = new Cell(new Phrase(11f, "\n\n\nLa inscripción en el Catastro no constituye título de dominio, ni sanea los vicios que tenga una titulación o una posesión. Art. 18, Resolución 2555/88 de IGAC.\n\n\nCordialmente, ", FontFactory.getFont("Helvetica", 11f, 0, new Color(0, 0, 0))));
        c1.setBorder(0);
        c1.setHorizontalAlignment("justify");
        table.addCell(c1);

        table.addCell(cellCuerpoBoletin("\n\n"));
        table.addCell(firmaMecanicaCuerpoCertificado(carpetaBoletines));
        table.addCell(cellCuerpoBoletin("MARIA ISABEL COGUA MORENO\n\n\n", "CENTER", "CENTER", 10F));
        table.addCell(cellFecCuerpoBoletin(fecha, razonSocialEmpresa));
        return table;
    }

    public File reporteCertificado(List<PropietarioPredio> propietarios, CertificadosVivienda certi, String rutaArchivos, String rutaImagenes, String fecha, String nombreArchivo) {
        Document d = new Document(PageSize.LETTER, 40f, 40f, 40f, 40f);
        PdfWriter writer = null;

        try {
            File pdfNew = new File(rutaArchivos + java.io.File.separator + nombreArchivo + "_.pdf");
            writer = PdfWriter.getInstance(d, new FileOutputStream(pdfNew));
            d.open();
            d.add(encabezadoCertificado2(rutaImagenes, fecha, certi.getNumeroRadicacion()));
            d.add(numbCertificado(certi.getNumeroCertificado()));
            d.add(encabezadoCertificado(rutaImagenes, fecha, certi.getNumeroCertificado()));
            d.add(cuerpoNombreCertificado(certi.getNombrePropietario(), certi.getTipoIdentificacion(), certi.getIdentificacionPropietario()));
            d.add(cuerpotextoCertificado(propietarios, fecha, certi.getUsuario().getCodEmpresa().getRazonSocial(), rutaImagenes));
            if (propietarios != null) {
                d.setMargins(18f, 18f, 18f, 18f);
                PdfReader reader = new PdfReader(getPropiedad("co.gov.uaecd.siic.facade.propiedades", "formatos") + "certificadoblanco_bogotaHumana.pdf");
                String fecArr[] = fecha.substring(0, 10).split("-");
                for (PropietarioPredio propietario: propietarios) {
                    d.newPage();
                    writer.getDirectContentUnder().addTemplate(writer.getImportedPage(reader, 1), 0, 0);
                    PdfContentByte canvas = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "W-" + certi.getNumeroRadicacion(), font), 520, 767, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, fecArr[2] + "/" + fecArr[1] + "/" + fecArr[0]), 470, 739, 0);
                    if (propietario.getPredio().getPropietarios() != null) {
                        for (int i = 0; i < propietario.getPredio().getPropietarios().size(); i++) {
                            PropietarioPredio p = (PropietarioPredio)propietario.getPredio().getPropietarios().get(i);
                            String nombreProp = p.getNombrePropietario();
                            if (p.getPrimerApellido() != null)
                                nombreProp = nombreProp + " " + p.getPrimerApellido();
                            if (p.getSegundoApellido() != null)
                                nombreProp = nombreProp + " " + p.getSegundoApellido();
                            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, (i + 1) + "", font1), 40, 641 - (i * 15), 0);
                            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, nombreProp, font1), 100, 641 - (i * 15), 0);
                            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, p.getPk().getTipoIdentificacion(), font1), 280, 641 - (i * 15), 0);
                            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, p.getPk().getNumeroIdentificacion() + "", font1), 350, 641 - (i * 15), 0);
                            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, p.getPorcentajeCopropiedad() + "", font1), 440, 641 - (i * 15), 0);
                            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, p.getPoseedor(), font1), 535, 641 - (i * 15), 0);
                            if (i == 4)
                                break;
                        }
                    }
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getTipoPropiedad().getCodigoTipoPropiedad() + "", font2), 72, 548, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getNumeroDocumento() + "", font2), 110, 548, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, (propietario.getPredio().getFechaDocumento() + "").substring(0, 10), font2), 190, 548, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getCiudadDocumento(), font2), 300, 548, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getNotaria(), font2), 420, 548, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getMatricula(), font2), 490, 548, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getDireccionReal(), font), 80, 455, 0);
                    if (propietario.getPredio().getDirSecundarias() != null && propietario.getPredio().getDirSecundarias().size() > 0)
                        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, ((DireccionSecundaria)propietario.getPredio().getDirSecundarias().get(0)).getDireccion(), font), 80, 418, 0);
                    if (propietario.getPredio().getHistoricoIdentificador() != null) {
                        propietario.getPredio().setHistoricoIdentificador(propietario.getPredio().direccionesAnteriores());
                        for (int i = 0; i < propietario.getPredio().getHistoricoIdentificador().size(); i++) {
                            HistoricoIdentificador dirAnt = (HistoricoIdentificador)propietario.getPredio().getHistoricoIdentificador().get(i);
                            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, dirAnt.getDireccionReal() + " FECHA: " + dirAnt.getFechaMutacion().toString().substring(0, 10), font1), 40, 385 - (i * 15), 0);
                            if (i == 4)
                                break;
                        }
                    }

                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getPk().getCodigoBarrio() + " " + propietario.getPredio().getPk().getCodigoManzana() + " " + propietario.getPredio().getPk().getCodigoPredio() + " " + propietario.getPredio().getPk().getCodigoConstruccion() + " " + propietario.getPredio().getPk().getCodigoResto(), font), 40, 300, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getCedulaCatastral(), font), 40, 260, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getChip(), font2), 60, 261, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getDestino().getCodigoDestino(), font2), 110, 233, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "Estrato        " + propietario.getPredio().getCodigoEstrato(), font3), 170, 233, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, darFormatoDouble(propietario.getPredio().getAreaTerreno(), 2), font2), 70, 198, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, darFormatoDouble(propietario.getPredio().getValorM2Terreno(), 2), font2), 200, 198, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, darFormatoDouble(propietario.getPredio().getAreaConstruida(), 2), font2), 70, 168, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, darFormatoDouble(propietario.getPredio().getValorM2Construccion(), 2), font2), 200, 168, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getTipoPropiedad().getDescripcionTipoPropiedad(), font1), 50, 140, 0);

                    if (propietario.getPredio().getUsos() != null && propietario.getPredio().getUsos().size() > 0)
                        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, propietario.getPredio().getUsos().get(0).getUso().getDescripcionUso(), font1), 120, 140, 0);

                    if (propietario.getPredio().getAvaluos() != null) {
                        for (int i = 0; i < propietario.getPredio().getAvaluos().size(); i++) {
                            Avaluos a = (Avaluos)propietario.getPredio().getAvaluos().get(i);
                            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, darFormatoInt(a.getValorAvaluo()), font4), 410, 459 - (i * 13), 0);
                            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "" + a.getAvaluoAno(), font4), 540, 459 - (i * 13), 0);
                            if (i == 4) {
                                break;
                            }
                        }
                    }

                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "Generado via web a los " + fecArr[2] + " días del mes de " + getNombreMes(Integer.parseInt(fecArr[1])) + " de " + fecArr[0] + " por ", font5), 320, 280, 0);
                    if (certi.getUsuario() == null)
                        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "el ciudadano", font5), 320, 270, 0);
                    else
                        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, certi.getUsuario().getCodEmpresa().getRazonSocial(), font5), 320, 270, 0);
                    Image img = Image.getInstance(getPropiedad("co.gov.uaecd.siic.facade.propiedades", "imagenes") + "firmaMIC.jpg");
                    img.scaleAbsoluteWidth(130f);
                    img.scaleAbsoluteHeight(25f);
                    img.setAbsolutePosition(405, 205);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "MARIA ISABEL COGUA MORENO", font5), 430, 200, 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "Responsable Área de Servicio al Usuario", font5), 410, 190, 0);
                    d.add(img);
                }
            }
            d.close();
            encryptorPdf(rutaArchivos, nombreArchivo);
            pdfNew.delete();
        }
        catch (ExceptionConverter ex0) {
            ex0.printStackTrace();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (DocumentException ex2) {
            ex2.printStackTrace();
        }
        catch (MalformedURLException ex3) {
            ex3.printStackTrace();
        }
        catch (IOException ex4) {
            ex4.printStackTrace();
        }
        catch (Exception ex5) {
            ex5.printStackTrace();
        }
        finally {
            writer.close();
            writer = null;
        }
        return new File(rutaArchivos + java.io.File.separator + nombreArchivo + ".pdf");
    }


    public File reporteBoletin(Boletines boletin, String rutaArchivos, String fecha, String nombre) {

        Document d = new Document(PageSize.LETTER, 18f, 18f, 18f, 18f);
        PdfReader reader = null;
        PdfWriter writer = null;
        fecha = fecha.substring(0, 10);
        String fecArr[] = fecha.split("-");
        try {
            reader = new PdfReader(getPropiedad("co.gov.uaecd.siic.facade.propiedades", "formatos") + "certificadoblanco_bogotaHumana.pdf");
            File pdfNew = new File(rutaArchivos + java.io.File.separator + nombre + "_.pdf");
            writer = PdfWriter.getInstance(d, new FileOutputStream(pdfNew));
            d.open();
            writer.setCompressionLevel(0);
            writer.getDirectContentUnder().addTemplate(writer.getImportedPage(reader, 1), 0, 0);
            PdfContentByte canvas = writer.getDirectContent();
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "W-" + boletin.getNumeroRadicacion(), font), 520, 767, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, fecArr[2] + "/" + fecArr[1] + "/" + fecArr[0]), 470, 739, 0);
            if (boletin.getPredio().getPropietarios() != null) {
                for (int i = 0; i < boletin.getPredio().getPropietarios().size(); i++) {
                    PropietarioPredio p = (PropietarioPredio)boletin.getPredio().getPropietarios().get(i);
                    String nombreProp = p.getNombrePropietario();
                    if (p.getPrimerApellido() != null)
                        nombreProp = nombreProp + " " + p.getPrimerApellido();
                    if (p.getSegundoApellido() != null)
                        nombreProp = nombreProp + " " + p.getSegundoApellido();
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, (i + 1) + "", font1), 40, 641 - (i * 15), 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, nombreProp, font1), 100, 641 - (i * 15), 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, p.getPk().getTipoIdentificacion(), font1), 280, 641 - (i * 15), 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, p.getPk().getNumeroIdentificacion() + "", font1), 350, 641 - (i * 15), 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, p.getPorcentajeCopropiedad() + "", font1), 440, 641 - (i * 15), 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(9F, p.getPoseedor(), font1), 535, 641 - (i * 15), 0);
                    if (i == 4)
                        break;
                }
            }
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getTipoPropiedad().getCodigoTipoPropiedad() + "", font2), 72, 548, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getNumeroDocumento() + "", font2), 110, 548, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, (boletin.getPredio().getFechaDocumento() + "").substring(0, 10), font2), 190, 548, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getCiudadDocumento(), font2), 300, 548, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getNotaria(), font2), 420, 548, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getMatricula(), font2), 490, 548, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getDireccionReal(), font), 80, 455, 0);

            if (boletin.getPredio().getDirSecundarias() != null && boletin.getPredio().getDirSecundarias().size() > 0)
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, ((DireccionSecundaria)boletin.getPredio().getDirSecundarias().get(0)).getDireccion(), font), 80, 418, 0);

            if (boletin.getPredio().getHistoricoIdentificador() != null) {
                boletin.getPredio().setHistoricoIdentificador(boletin.getPredio().direccionesAnteriores());
                if (boletin.getPredio().getHistoricoIdentificador() != null) {
                    for (int i = 0; i < boletin.getPredio().getHistoricoIdentificador().size(); i++) {
                        HistoricoIdentificador dirAnt = (HistoricoIdentificador)boletin.getPredio().getHistoricoIdentificador().get(i);
                        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, dirAnt.getDireccionReal() + " FECHA: " + dirAnt.getFechaMutacion().toString().substring(0, 10), font1), 40, 390 - (i * 15), 0);
                        if (i == 4)
                            break;
                    }
                }
            }

            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getPk().getCodigoBarrio() + " " + boletin.getPredio().getPk().getCodigoManzana() + " " + boletin.getPredio().getPk().getCodigoPredio() + " " + boletin.getPredio().getPk().getCodigoConstruccion() + " " + boletin.getPredio().getPk().getCodigoResto(), font), 40, 300, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getCedulaCatastral(), font), 40, 260, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getChip(), font2), 60, 261, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getDestino().getCodigoDestino(), font2), 110, 233, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "Estrato        " + boletin.getPredio().getCodigoEstrato(), font3), 170, 233, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, darFormatoDouble(boletin.getPredio().getAreaTerreno(), 2), font2), 70, 198, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, darFormatoDouble(boletin.getPredio().getValorM2Terreno(), 2), font2), 200, 198, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, darFormatoDouble(boletin.getPredio().getAreaConstruida(), 2), font2), 70, 168, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, darFormatoDouble(boletin.getPredio().getValorM2Construccion(), 2), font2), 200, 168, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getTipoPropiedad().getDescripcionTipoPropiedad(), font1), 50, 140, 0);

            if (boletin.getPredio().getUsos() != null && boletin.getPredio().getUsos().size() > 0)
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getPredio().getUsos().get(0).getUso().getDescripcionUso(), font1), 120, 140, 0);

            if (boletin.getPredio().getAvaluos() != null) {
                for (int i = 0; i < boletin.getPredio().getAvaluos().size(); i++) {
                    Avaluos a = (Avaluos)boletin.getPredio().getAvaluos().get(i);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, darFormatoInt(a.getValorAvaluo()), font4), 410, 459 - (i * 13), 0);
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "" + a.getAvaluoAno(), font4), 540, 459 - (i * 13), 0);
                    if (i == 4) {
                        break;
                    }
                }
            }

            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "Generado via web a los " + fecArr[2] + " días del mes de " + getNombreMes(Integer.parseInt(fecArr[1])) + " de " + fecArr[0] + " por ", font5), 320, 280, 0);
            if (boletin.getUsuario() == null)
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "el ciudadano", font5), 320, 270, 0);
            else
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, boletin.getUsuario().getCodEmpresa().getRazonSocial(), font5), 320, 270, 0);
            Image img = Image.getInstance(getPropiedad("co.gov.uaecd.siic.facade.propiedades", "imagenes") + "firmaMIC.jpg");
            img.scaleAbsoluteWidth(130f);
            img.scaleAbsoluteHeight(25f);
            img.setAbsolutePosition(405, 205);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "MARIA ISABEL COGUA MORENO", font5), 430, 200, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(11F, "Responsable Área de Servicio al Usuario", font5), 410, 190, 0);
            d.add(img);

            d.close();
            encryptorPdf(rutaArchivos, nombre);
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (DocumentException ex) {
            ex.printStackTrace();
            System.err.println("El error es : " + ex.getMessage());
        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {

            d = null;
        }
        return new File(rutaArchivos + java.io.File.separator + nombre + ".pdf");
    }

    private void encryptorPdf(String rutaArchivos, String nombre) {
        PdfReader reader = null;
        FileOutputStream outputStream = null;
        try {

            reader = new PdfReader(rutaArchivos + java.io.File.separator + nombre + "_.pdf");
            outputStream = new FileOutputStream(rutaArchivos + java.io.File.separator + nombre + ".pdf");
            PdfEncryptor.encrypt(reader, outputStream, null, null, PdfWriter.ALLOW_PRINTING, false);
            reader.close();
            reader = null;
            outputStream.close();
            outputStream = null;


            File fileDel = new File(rutaArchivos + java.io.File.separator + nombre + "_.pdf");
            fileDel.delete();
            fileDel = null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int digitoVerificacion(String numero) {
        int num = 0;
        char[] numeros = numero.toCharArray();
        int suma = 0;
        int multiplicador = 1;
        for (int i = 0; i < numeros.length; i++) {
            char nume = numeros[i];
            Integer n = Integer.parseInt(nume + "");
            suma = suma + (n.intValue() * multiplicador);
            if (multiplicador == 1)
                multiplicador = 3;
            else
                multiplicador = 1;
        }
        String sumaS = suma + "";
        //System.out.println(sumaS.length());
        if (sumaS.length() == 1 || suma == 10)
            num = 10 - suma;
        else if (sumaS.length() == 2) {
            Integer dec = (Integer.parseInt(sumaS.substring(0, 1)) + 1) * 10;
            num = dec.intValue() - suma;
        }
        //System.out.println(num);
        return num;
    }


    private Long getNumRadicacionWeb(String valor, boolean check) {
        Long numero = new Long(0);
        if (check)
            checkEntityManager();
        String query = "SELECT NUM_RADICACION_WEB.NEXTVAL FROM DUAL";
        if (valor.equals("CONS"))
            query = "SELECT cod_boletin.NEXTVAL FROM DUAL";
        else if (valor.equals("CERT"))
            query = "SELECT cod_certificado.NEXTVAL FROM DUAL";
        List<Vector> vectores = em.createNativeQuery(query).getResultList();
        vectores = em.createNativeQuery(query).getResultList();
        for (Vector vector: vectores) {
            numero = Long.parseLong(vector.get(0).toString());
            break;
        }
        if (check)
            closeEM();
        return numero;
    }

    public String addCaratulaMutacion(CaratulaMutacionWeb caratula) {
        String s = "";
        getUsuario();
        checkEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            caratula.setCodUsuario(usuario);
            caratula.setFecha(new java.sql.Timestamp((new Date()).getTime()));
            tx.begin();
            CaratulaMutacionWeb caratulaX = em.find(CaratulaMutacionWeb.class, caratula.getPk());
            if (caratulaX == null) {
                caratula.setCiudadDocumento(caratula.getCiudadDocumento().toUpperCase());
                caratula.setNombreEdificio(caratula.getNombreEdificio().toUpperCase());
                em.persist(caratula);
                em.merge(caratula);
            }
            else {
                caratulaX.setAreaComunConstruida(caratula.getAreaComunConstruida());
                caratulaX.setAreaTerrenoEtapa(caratula.getAreaTerrenoEtapa());
                caratulaX.setAreaTerrenoMatriz(caratula.getAreaTerrenoMatriz());
                caratulaX.setCiudadDocumento(caratula.getCiudadDocumento().toUpperCase());
                caratulaX.setDocumento(caratula.getDocumento());
                caratulaX.setEtapaNumero(caratula.getEtapaNumero());
                caratulaX.setFecha(caratula.getFecha());
                caratulaX.setFechaDocumento(caratula.getFechaDocumento());
                caratulaX.setNombreEdificio(caratula.getNombreEdificio().toUpperCase());
                caratulaX.setNotaria(caratula.getNotaria());
                caratulaX.setNumeroDocumento(caratula.getNumeroDocumento());
                caratulaX.setNumeroUnidades(caratula.getNumeroUnidades());
                caratulaX.setPisos(caratula.getPisos());
                caratulaX.setTipoPh(caratula.getTipoPh());
            }

            //em.flush();

            tx.commit();
            s = "OK";
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            tx = null;
            this.closeEM();
        }
        return s;
    }

    public CaratulaMutacionWeb getCaratulaRadicacion(boolean check, long anoRadicacion, long numeroRadicacion) {
        CaratulaMutacionWeb caratula = new CaratulaMutacionWeb();
        if (check)
            checkEntityManager();
        em.clear();
        try {
            caratula = (CaratulaMutacionWeb)em.createNamedQuery("CaratulaMutacionWeb.getCaratulaUsuario").setParameter("parAnioRad", anoRadicacion).setParameter("parNumero", numeroRadicacion).setParameter("parCodUsuario", codUsuarioCon).getSingleResult();
        }
        catch (javax.persistence.NoResultException nre) {
            //nre.printStackTrace();
            caratula = null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            caratula = null;
        }
        finally {
            if (check)
                closeEM();
        }
        return caratula;
    }

    public String addPredioDesenglobePH(boolean check, PredioMutacionWeb predioPH) {
        String s = "";
        if (check)
            checkEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            predioPH.setCodigoTraductor(getCodeAddress(false, predioPH.getDireccionReal()).substring(1, 46));

            tx.begin();
            em.persist(predioPH);
            //em.flush();
            em.merge(predioPH);
            tx.commit();
            s = "OK";
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            tx = null;
            if (check)
                closeEM();
        }
        return s;
    }

    public String addPopietarioDesenglobePH(boolean check, PropietarioMutacionWeb propietarioPH) {
        String s = "";
        if (check)
            checkEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(propietarioPH);
            //em.flush();
            em.merge(propietarioPH);
            tx.commit();
            s = "OK";
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            tx = null;
            if (check)
                closeEM();
        }
        return s;
    }

    public List<PredioMutacionWeb> getPrediosPH(boolean check, long anio, long numradicacion) {
        if (check)
            checkEntityManager();
        em.clear();
        List<PredioMutacionWeb> prediosPH = new ArrayList<PredioMutacionWeb>();
        try {
            prediosPH = em.createNamedQuery("getPrediosPH").setParameter("parAnioRad", anio).setParameter("parNumero", numradicacion).getResultList();
        }
        catch (javax.persistence.NoResultException nre) {
            nre.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (check)
                closeEM();
        }
        return prediosPH;
    }

    public List<PropietarioMutacionWeb> getPropietariosPH(boolean check, long anio, long numradicacion) {
        if (check)
            checkEntityManager();
        em.clear();
        List<PropietarioMutacionWeb> propietariosPH = new ArrayList<PropietarioMutacionWeb>();
        try {
            propietariosPH = em.createNamedQuery("getPropietariosPH").setParameter("parAnioRad", anio).setParameter("parNumero", numradicacion).getResultList();
        }
        catch (javax.persistence.NoResultException nre) {
            nre.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (check)
                closeEM();
        }
        return propietariosPH;
    }

    public PredioIdentificador ubicarPredio(String address) {
        PredioIdentificador p = null;
        checkEntityManager();
        String code = getCodeAddress(false, address);
        if (code.substring(0, 1).equals("*")) {
            p = new PredioIdentificador();
            p.setChip("DirErrada");
        }
        else {
            em.clear();
            try {
                p = (PredioIdentificador)em.createNamedQuery("getPredio").setParameter("parCodTraductor", code.substring(0, 47)).getSingleResult();
                p.setDestinosHacendario(this.getDestinoHacendarioPredio(p.getChip()));
            }
            catch (javax.persistence.NoResultException nex) {
                try {
                    p = isDireccion(code);
                    p.setDestinosHacendario(this.getDestinoHacendarioPredio(p.getChip()));
                }
                catch (Exception ex) {
                    return null;
                }
            }
        }
        closeEM();
        return p;
    }

    public PredioIdentificador ubicarPredioChip(String chip) {
        return ubicarPredioParametro(chip, "PredioIdentificador.getPredioSNChip", "parChip");
    }

    public PredioIdentificador ubicarPredioCedulaCat(String cedulaCat) {
        return ubicarPredioParametro(cedulaCat, "PredioIdentificador.getPredio", "parCedulaCat");
    }

    public PredioIdentificador ubicarPredioMatricula(String matricula) {
        return ubicarPredioParametro(matricula, "PredioIdentificador.getPredioSNMatricula", "parMatricula");
    }

    private PredioIdentificador ubicarPredioParametro(String parametro, String query, String nombreParametro) {
        checkEntityManager();
        PredioIdentificador p = null;
        try {
            em.clear();
            p = (PredioIdentificador)em.createNamedQuery(query).setParameter(nombreParametro, parametro).getSingleResult();
            p.setDestinosHacendario(this.getDestinoHacendarioPredio(p.getChip()));

        }
        catch (javax.persistence.NoResultException nre) {
            return null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            closeEM();
        }
        return p;
    }

    @SuppressWarnings("unused")
    private Object getObjetoConsulta(String parametro, String query, String nombreParametro) {
        checkEntityManager();
        Object o = null;
        try {
            em.clear();
            o = (Object)em.createNamedQuery(query).setParameter(nombreParametro, parametro).getSingleResult();
        }
        catch (javax.persistence.NoResultException nre) {
            return null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            closeEM();
        }
        return o;
    }

    @SuppressWarnings("unused")
    private List getObjetosConsulta(String parametro, String query, String nombreParametro) {
        checkEntityManager();
        List objects = new ArrayList();
        try {
            em.clear();
            objects = em.createNamedQuery(query).setParameter(nombreParametro, parametro).getResultList();
        }
        catch (javax.persistence.NoResultException nre) {
            return null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            closeEM();
        }
        return objects;
    }

    private List getObjetosConsulta(long parametro, String query, String nombreParametro) {
        checkEntityManager();
        List objects = new ArrayList();
        try {
            em.clear();
            objects = em.createNamedQuery(query).setParameter(nombreParametro, parametro).getResultList();
        }
        catch (javax.persistence.NoResultException nre) {
            return null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            closeEM();
        }
        return objects;
    }

    public List<PropietarioPredio> getPropietariosPredioID(long identificacion) {
        List<PropietarioPredio> pps = (List<PropietarioPredio>)getObjetosConsulta(identificacion, "PropietarioPredio.getPropietario", "parIdentificacion");
        if (pps != null) {
            if (pps.size() <= 0)
                return null;
        }
        return pps;
    }


    public PredioIdentificador getPrediosIdentificador(String codBarrio, String codManzana, String codPredio, String codConstruccion, String codResto) {
        PredioIdentificador p = null;
        try {
            checkEntityManager();
            em.clear();
            p = (PredioIdentificador)em.createNamedQuery("getPredioCS").setParameter("parCodBarrio", codBarrio).setParameter("parCodManzana", codManzana).setParameter("parCodPredio", codPredio).setParameter("parCodConstruccion", codConstruccion).setParameter("parCodResto", codResto).getSingleResult();
        }
        catch (Exception ex) {
            //ex.printStackTrace();
        }
        finally {
            closeEM();
        }
        return p;
    }

    public boolean isActualizacion(String codBarrio, int Anio) {
        boolean b = false;
        checkEntityManager();
        em.clear();
        List<Barrio> barrios = em.createNamedQuery("getBarrioAct").setParameter("parCodBarrio", codBarrio).setParameter("parAnioActualizacion", Anio).getResultList();
        if (barrios.size() > 0)
            b = true;
        closeEM();
        return b;
    }

    public List<Barrio> getBarriosActualizacion(int Anio) {
        checkEntityManager();
        em.clear();
        List<Barrio> barrios = em.createNamedQuery("getBarriosAct").setParameter("parAnioActualizacion", Anio).getResultList();
        closeEM();
        return barrios;
    }

    public Barrio getBarrio(String codBarrio) {
        checkEntityManager();
        em.clear();
        Barrio barrio = (Barrio)em.createNamedQuery("Barrio.getBarrio").setParameter("parCodBarrio", codBarrio).getSingleResult();
        closeEM();
        return barrio;
    }

    public DestinosHacendario getDestinoHacendarioPredio(String chip) {
        checkEntityManager();
        em.clear();
        DestinosHacendario destinosHacendario = (DestinosHacendario)em.createNamedQuery("DestinosHacendario.getPredio").setParameter("parChip", chip).getSingleResult();
        closeEM();
        return destinosHacendario;
    }

    public Destino getDestino(String codigo) {
        checkEntityManager();
        Destino d = new Destino();
        em.clear();
        d = (Destino)em.createNamedQuery("getDestino").setParameter("parCodigo", codigo).getSingleResult();
        closeEM();
        return d;
    }

    public void removerPrediosPH(boolean check, long anio, long numradicacion) {
        if (check)
            checkEntityManager();
        em.clear();
        List<PredioMutacionWeb> prediosPH = new ArrayList<PredioMutacionWeb>();
        EntityTransaction tx = em.getTransaction();
        try {
            prediosPH = em.createNamedQuery("getPrediosPH").setParameter("parAnioRad", anio).setParameter("parNumero", numradicacion).getResultList();
            tx.begin();
            for (PredioMutacionWeb predioPh: prediosPH) {
                em.remove(predioPh);
            }
            tx.commit();
        }
        catch (javax.persistence.NoResultException nre) {
            tx.rollback();
            nre.printStackTrace();
        }
        catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        }
        finally {
            if (check)
                closeEM();
            tx = null;
        }

    }

    public void removerPropietariosPH(boolean check, long anio, long numradicacion) {
        if (check)
            checkEntityManager();
        em.clear();
        List<PropietarioMutacionWeb> propietariosPH = new ArrayList<PropietarioMutacionWeb>();
        EntityTransaction tx = em.getTransaction();
        try {
            propietariosPH = em.createNamedQuery("getPropietariosPH").setParameter("parAnioRad", anio).setParameter("parNumero", numradicacion).getResultList();
            tx.begin();
            for (PropietarioMutacionWeb propietarioPh: propietariosPH) {
                em.remove(propietarioPh);
            }
            tx.commit();
        }
        catch (javax.persistence.NoResultException nre) {
            tx.rollback();
            nre.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (check)
                closeEM();
        }
    }

    private PredioIdentificador isDireccion(String code) {
        PredioIdentificador p = null;
        checkEntityManager();
        try {

            Query query = em.createNativeQuery("SELECT DISTINCT(codigo_barrio), codigo_manzana, " + "codigo_predio, codigo_construccion, codigo_resto FROM HISTORICO_IDENTIFICADOR WHERE " + "codigo_traductor = UPPER('" + code.substring(0, 47) + "')");
            Vector vec = (Vector)query.getSingleResult();

            em.clear();
            p = (PredioIdentificador)em.createNamedQuery("getPredioCS").setParameter("parCodBarrio", vec.get(0).toString()).setParameter("parCodManzana", vec.get(1).toString()).setParameter("parCodPredio", vec.get(2).toString()).setParameter("parCodConstruccion", vec.get(3).toString()).setParameter("parCodResto", vec.get(4).toString()).getSingleResult();
        }
        catch (Exception ex) {
            //System.out.println("<<<<<<<<<<<<< OJO >>>>>>>>>>>>> Código del Traductor de Direcciones ------------> " + code);
            System.out.println("Consulta de una dirección inexistente ---->[" + code + "]");
            apl.addException(this.getClass().getPackage().getName(), "Consulta de una dirección inexistente ---->[" + code + "]", "isDireccion(String code)", codUsuarioCon, ex.getMessage(), "EXCEPTION");
        }
        finally {
            closeEM();
        }
        return p;
    }

    public boolean isBloqueado(String codBarrio, String codManzana, String codPredio, String codConstruccion, String codResto, String chip) {
        boolean b = false;
        checkEntityManager();
        em.clear();
        List bloq = em.createNamedQuery("getBloqueo").setParameter("parCodBarrio", codBarrio).setParameter("parCodManzana", codManzana).setParameter("parCodPredio", codPredio).setParameter("parCodConstruccion", codConstruccion).setParameter("parCodResto", codResto).getResultList();
        if (bloq != null && bloq.size() > 0)
            b = true;
        else {
            if (chip != null)
                bloq = em.createNamedQuery("getBloqueoChip").setParameter("parChip", chip).getResultList();
            if (bloq != null && bloq.size() > 0)
                b = true;
        }
        closeEM();
        return b;
    }

    public boolean isRestriccion(String codBarrio, String codManzana, String codPredio, String codConstruccion, String codResto, String chip, String restriccion) {
        boolean b = false;
        checkEntityManager();
        em.clear();
        List bloq = em.createNamedQuery("RestriccionPredio.getPredioRestriccion").setParameter("parCodBarrio", codBarrio).setParameter("parCodManzana", codManzana).setParameter("parCodPredio", codPredio).setParameter("parCodConstruccion", codConstruccion).setParameter("parCodResto", codResto).setParameter("parRestriccion", restriccion).getResultList();
        if (bloq != null && bloq.size() > 0)
            b = true;
        else {
            if (chip != null)
                bloq = em.createNamedQuery("RestriccionPredio.getPredioRestriccionChip").setParameter("parChip", chip).setParameter("parRestriccion", restriccion).getResultList();
            if (bloq != null && bloq.size() > 0)
                b = true;
        }
        closeEM();
        return b;
    }

    public String getCodeAddress(boolean check, String address) {
        if (check)
            checkEntityManager();
        address = address.replaceAll("//////", "#");
        address = address.toUpperCase();
        String code = "Direccion errada";
        Query query = em.createNativeQuery("SELECT UPPER(traductor('" + address + "')) AS CODE FROM DUAL");
        List vectores = query.getResultList();
        for (int i = 0; i < vectores.size(); i++) {
            Vector vec = (Vector)vectores.get(i);
            if (vec.get(0) != null)
                code = vec.get(0).toString();
        }
        if (check)
            closeEM();
        return code;
    }

    public AplException getApl() {
        return apl;
    }

    public void setApl(AplException apl) {
        this.apl = apl;
    }

    public int getCodUsuarioCon() {
        return codUsuarioCon;
    }

    public void setCodUsuarioCon(int codUsuarioCon) {
        this.codUsuarioCon = codUsuarioCon;
    }

    public String getIpRemoto() {
        return ipRemoto;
    }

    public void setIpRemoto(String ipRemoto) {
        apl.setIpRemoto(ipRemoto);
        this.ipRemoto = ipRemoto;
    }

    public List<PasoRadicacion> getPasoRadicacion(boolean check, long anoRadicacion, long numeroRadicacion) {
        List<PasoRadicacion> pasos = new ArrayList<PasoRadicacion>();
        if (check)
            checkEntityManager();
        em.clear();
        try {
            pasos = em.createNamedQuery("getPasoRadicacion").setParameter("parAnioRadicacion", anoRadicacion).setParameter("parNumero", numeroRadicacion).getResultList();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            pasos = null;
        }
        finally {
            if (check)
                closeEM();
            if (pasos != null && pasos.size() <= 0)
                pasos = null;
        }
        return pasos;
    }

    public Radicacion validarRadicacionDesenglobe(boolean check, long anoRadicacion, long numeroRadicacion) {
        Radicacion rad = null;
        if (check)
            checkEntityManager();
        em.clear();
        rad = getRadicacion(false, anoRadicacion, numeroRadicacion);
        if (rad != null) {
            if (rad.getEstadoRadicacion().equals("V")) {
                if (rad.getTipoTramite().getCodigoTramite() == 22) {
                    List<PasoRadicacion> pasos = getPasoRadicacion(false, anoRadicacion, numeroRadicacion);
                    boolean b = false;
                    for (PasoRadicacion paso: pasos) {
                        if (paso.getCodigoActividad().getCodigoActividad() == 54) {
                            b = true;
                            break;
                        }
                    }
                    if (b) {
                        rad = null;
                        rad = new Radicacion();
                        rad.setNombreSolicitante("PASO54");
                    }
                }
                else {
                    rad = null;
                    rad = new Radicacion();
                    rad.setNombreSolicitante("NOTIPO");
                }
            }
            else {
                rad = null;
                rad = new Radicacion();
                rad.setNombreSolicitante("NOESTADO");
            }
        }
        else {
            rad = null;
            rad = new Radicacion();
            rad.setNombreSolicitante("NOEXISTE");
        }
        return rad;
    }

    public Radicacion getRadicacion(boolean check, long anoRadicacion, long numeroRadicacion) {
        Radicacion rad = null;
        if (check)
            checkEntityManager();
        em.clear();
        try {
            rad = (Radicacion)em.createNamedQuery("Radicacion.getRadicacionAnio").setParameter("parAnioRad", anoRadicacion).setParameter("parNumero", numeroRadicacion).getSingleResult();
            apl.addSelect(this.getClass().getPackage().getName(), "RADICACION CONSULTADA ...! (" + anoRadicacion + " - " + numeroRadicacion + ")", "getRadicacion(boolean check, long anoRadicacion, long numeroRadicacion)", "RADICACION", ipRemoto, codUsuarioCon);
        }
        catch (javax.persistence.NoResultException nre) {
            apl.addException(this.getClass().getPackage().getName(), "NO EXISTE LA RADICACION CONSULTADA ---->[" + anoRadicacion + " - " + numeroRadicacion + "]", "getRadicacion(boolean check, long anoRadicacion, long numeroRadicacion)", codUsuarioCon, nre.getMessage(), "EXCEPTION");
            System.out.println("NO EXISTE LA RADICACION CONSULTADA ......! (" + anoRadicacion + " - " + numeroRadicacion + ")");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (check)
                closeEM();
        }
        return rad;
    }
    
    public RadicacionMutacion getMutacion(boolean check,long anoRadicacion, long numeroRadicacion) {
    
        RadicacionMutacion mut = null;
        
        if (check)
            checkEntityManager();

        try {

           mut = (RadicacionMutacion)em.createNamedQuery("RadicacionMutacion.obtMut").setParameter("parAnioRadicacion", anoRadicacion).setParameter("parNumeroRadicacion", numeroRadicacion).getSingleResult();
        
        }
        catch (NoResultException e) {
            apl.addException(this.getClass().getPackage().getName(), e.getMessage(), "getMutacion(long anoRadicacion, long numeroRadicacion)", codUsuarioCon, e.getMessage(), "EXCEPTION");
            mut = null;
        }
        finally {
            if (check)
                closeEM();
        }
        
        return mut;
    }
    
    public Parametros getUrl(boolean check, String parametro) {
    
        Parametros par = new Parametros();
        
        
        if (check)
            checkEntityManager();
            em.clear();
        

        try {
            
            
           par = (Parametros)em.createNamedQuery("Parametros.findForParameter").setParameter("parNombreParametro", parametro).getSingleResult();
           
        
        }
        catch (NoResultException e) {
            apl.addException(this.getClass().getPackage().getName(), e.getMessage(), "getUrl(String parametro)", codUsuarioCon, e.getMessage(), "EXCEPTION");
            par = null;
        }
        finally {
            if (check)
                closeEM();
        }
        
        //System.out.println("-----------------------par-------------"+par.getDescripcion());
        
        return par;
    }
    
    



    /*public int addPropietariosDesenglobePH(ArrayList filas, CaratulaMutacionWeb caratula){
		int cantidadG = 0;
		PropietarioMutacionWebPK propPK = new PropietarioMutacionWebPK();
		propPK.setAnoRadicacion(caratula.getPk().getAnoRadicacion());
		propPK.setNumeroRadicacion(caratula.getPk().getNumeroRadicacion());
		propPK.setNumeroPropietario(1);
		
		for (int i=0; i<filas.size(); i++){
			ArrayList columnas = (ArrayList)filas.get(i);
			PropietarioMutacionWeb prop = new PropietarioMutacionWeb();
			propPK.setConsecutivoPredio(((Integer)columnas.get(1)).intValue());
			prop.setPk(propPK);
			
			prop.setNombrePropietario((String)columnas.get(3));
			String[] nomIdentif = ((String)columnas.get(2)).split(" ");
			prop.setTipoIdentificacion(nomIdentif[0]);
			prop.setNumeroIdentificacion(nomIdentif[1]);
			prop.setPrimerApellido((String)columnas.get(4));
			prop.setSegundoApellido((String)columnas.get(5));
			if (addPopietarioDesenglobePH(true, prop).equals("OK"))
				cantidadG++;
		}
		
		return cantidadG;
	}*/

    /*public int addPrediosDesenglobePH(ArrayList filas, CaratulaMutacionWeb caratula){
		int cantidadG = 0;
		PredioMutacionWebPK predPK = new PredioMutacionWebPK();
		predPK.setAnoRadicacion(caratula.getPk().getAnoRadicacion());
		predPK.setNumeroRadicacion(caratula.getPk().getNumeroRadicacion());
		for (int i=0; i<filas.size(); i++){
			ArrayList columnas = (ArrayList)filas.get(i);
			PredioMutacionWeb pred = new PredioMutacionWeb();
			predPK.setConsecutivoPredio(i+1);
			pred.setPk(predPK);
			pred.setMatricula(new BigDecimal((Long)columnas.get(1)));
			pred.setDireccionReal((String)columnas.get(2));
			pred.setPorcentajeCopropiedad(new BigDecimal((Long)columnas.get(3)));
			pred.setAreaPrivadaLote(new BigDecimal(((Long)columnas.get(3)).longValue() * caratula.getAreaTerrenoEtapa().longValue()));
			pred.setAreaPrivadaConstruida(new BigDecimal((Long)columnas.get(5)));
			if (addPredioDesenglobePH(true, pred).equals("OK"))
				cantidadG++;
		}
		return cantidadG;
	}*/

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        if (codUsuarioCon > 0) {
            checkEntityManager();
            em.clear();
            usuario = em.find(Usuario.class, codUsuarioCon);
            closeEM();
        }
        return usuario;
    }

    public String getPropiedad(String archivo, String propiedad) {
        ResourceBundle rb = ResourceBundle.getBundle(archivo);
        return rb.getString(propiedad);
    }

    public static String darFormatoDouble(double numero, int decimales) {
        String numRed = "";
        String numFormato = numero + "";
        double numRedDecimales = redondeoDecimales(numero, decimales);
        String decimal = ((numRedDecimales + "").replace('.', '_')).split("_")[1];
        Double numEntFormato = new Double(numRedDecimales);
        if (!((numRedDecimales + "").replace('.', '_')).split("_")[0].equals("-0")) {
            numRed = darFormatoInt(numEntFormato.intValue());
        }
        else {
            numRed = "-" + darFormatoInt(numEntFormato.intValue());
            ;
        }
        numFormato = numRed + "." + decimal;
        return numFormato;
    }

    public static double redondeoDecimales(double numero, int numDecimales) {
        double numeroFinal = numero;
        Double numeroDB = new Double(numero);
        int num = numeroDB.intValue();
        int cantDecimales = 1;
        for (int i = 0; i < numDecimales; i++) {
            cantDecimales = cantDecimales * 10;
        }
        int canDecRed = cantDecimales * 10;
        double partdec = decimalPart(numero) * cantDecimales;
        double partdecM = decimalPart(numero) * canDecRed;
        Double dec = new Double(decimalPart(numero) * cantDecimales);
        if (sign(numero) == -1) {
            partdec = partdec * -1;
            partdecM = partdecM * -1;
        }
        double partedecD = java.lang.Math.floor(partdec);
        double partedecDM = java.lang.Math.rint(partdecM);
        int decimales = dec.intValue();
        if (sign(numero) == -1) {
            decimales = decimales * -1;
        }
        double vlrRedD = partedecDM - partedecD * 10;
        if (vlrRedD > 4) {
            decimales = decimales + 1;
        }
        double decimal = Double.parseDouble(decimales + "");
        if (sign(numero) == -1) {
            decimal = decimal * -1;
        }
        numeroFinal = num + (decimal / cantDecimales);
        return numeroFinal;
    }

    public static String darFormatoInt(double d) {
        DecimalFormat formato = new DecimalFormat("#,###,###");
        return formato.format(d);
    }

    public static double decimalPart(double d) {
        return d - (long)d;
    }

    public static int sign(double n) {
        if (n < 0) {
            return -1;
        }
        if (n > 0) {
            return 1;
        }
        return 0;
    }

    public String getNombreMes(int mes) {
        String mesNomb = "";
        switch (mes) {
            case 1:
                mesNomb = "Enero";
                break;
            case 2:
                mesNomb = "Febrero";
                break;
            case 3:
                mesNomb = "Marzo";
                break;
            case 4:
                mesNomb = "Abril";
                break;
            case 5:
                mesNomb = "Mayo";
                break;
            case 6:
                mesNomb = "Junio";
                break;
            case 7:
                mesNomb = "Julio";
                break;
            case 8:
                mesNomb = "Agosto";
                break;
            case 9:
                mesNomb = "Septiembre";
                break;
            case 10:
                mesNomb = "Octubre";
                break;
            case 11:
                mesNomb = "Noviembre";
                break;
            case 12:
                mesNomb = "Diciembre";
                break;
        }
        return mesNomb;
    }

    public static String darFormatoInt(long d) {
        DecimalFormat formato = new DecimalFormat("#,###,###");
        return formato.format(d);
    }

    public Usuario getUsuario(int codigo) {
        checkEntityManager();
        em.clear();
        Usuario usuario = em.find(Usuario.class, codigo);
        closeEM();
        return usuario;
    }
}
