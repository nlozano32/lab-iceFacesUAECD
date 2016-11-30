package co.gov.uaecd.siic.entities;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PREDIO", schema = "OPS$SIIC")
 @NamedQueries({@NamedQuery(name = "Predio.findAll", query = "select o from Predio o")
               ,@NamedQuery(name = "Predio.getPredioBMP", query = "SELECT p FROM Predio p WHERE p.pk.codigoBarrio = :parCodBarrio AND p.pk.codigoManzana = :parCodManzana AND p.pk.codigoPredio = :parCodPredio ")
              })
public class Predio implements Serializable {
    
    @EmbeddedId
    private PredioPK pk;
    
    @Column(name="ADICIONAL_DOCUMENTO")
    private String adicionalDocumento;
    @Column(name="ANO_RESOLUCION_CREACION")
    private Long anoResolucionCreacion;
    @Column(name="AREA_COMUN_TERRENO")
    private Double areaComunTerreno;
    @Column(name="AREA_CONSTRUIDA")
    private Double areaConstruida;
    @Column(name="AREA_PRIVADA_TERRENO")
    private Double areaPrivadaTerreno;
    @Column(name="AREA_TERRENO")
    private Double areaTerreno;
    @Column(name="AVALUO_ANO")
    private Long avaluoAno;
    private String circulo;
    @Column(name="CIUDAD_DOCUMENTO")
    private String ciudadDocumento;
    @Column(name="CLASE_PREDIO")
    private String clasePredio;
    @Column(name="CODIGO_DESTINO")
    private String codigoDestino;
    @Column(name="CODIGO_ESTRATO")
    private Long codigoEstrato;
    @Column(name="CODIGO_FUENTE")
    private String codigoFuente;
    @Column(name="CONSERVACION_HISTORICA")
    private String conservacionHistorica;
    @Column(name="DIRECCION_REAL")
    private String direccionReal;
    @Column(name="DIREC_CORRESPONDENCIA")
    private String direcCorrespondencia;
    private Long documento;
    @Column(name="EXISTENCIA_CARTOGRAFICA")
    private String existenciaCartografica;
    @Column(name="EXPIRACION_DOCUMENTO")
    private Timestamp expiracionDocumento;
    @Column(name="FECHA_ACTUALIZACION")
    private Timestamp fechaActualizacion;
    @Column(name="FECHA_AUTOAVALUO")
    private Timestamp fechaAutoavaluo;
    @Column(name="FECHA_AVALUO_PRIMERA")
    private Timestamp fechaAvaluoPrimera;
    @Column(name="FECHA_AVALUO_SEGUNDA")
    private Timestamp fechaAvaluoSegunda;
    @Column(name="FECHA_CONSERVACION")
    private Timestamp fechaConservacion;
    @Column(name="FECHA_DOCUMENTO")
    private Timestamp fechaDocumento;
    @Column(name="FECHA_INCORPORACION")
    private Timestamp fechaIncorporacion;
    @Column(name="FECHA_REVISION")
    private Timestamp fechaRevision;
    private Long log;
    @Column(name="MARCA_AUTOAVALUO")
    private String marcaAutoavaluo;
    @Column(name="MARCA_AVALUO_PRIMERA")
    private String marcaAvaluoPrimera;
    @Column(name="MARCA_AVALUO_SEGUNDA")
    private String marcaAvaluoSegunda;
    @Column(name="MARCA_DIRECCION")
    private String marcaDireccion;
    @Column(name="MARCA_FORMACION")
    private String marcaFormacion;
    @Column(name="MARCA_PH")
    private String marcaPh;
    @Column(name="MARCA_REVISION")
    private String marcaRevision;
    @Column(name="MARCA_SECTOR_MAL")
    private String marcaSectorMal;
    private String matricula;
    @Column(name="MAX_NUM_PISO")
    private Long maxNumPiso;
    private String notaria;
    @Column(name="NUMERO_DOCUMENTO")
    private Long numeroDocumento;
    @Column(name="NUMERO_MEJORAS")
    private Long numeroMejoras;
    @Column(name="NUMERO_RESOLUCION_CREACION")
    private Long numeroResolucionCreacion;
    @Column(name="PORCENTAJE_COPROPIEDAD")
    private Double porcentajeCopropiedad;
    @Column(name="TIPO_MEJORA")
    private String tipoMejora;
    @Column(name="TIPO_PROPIEDAD")
    private String tipoPropiedad;
    private String vacante;
    @Column(name="VALOR_AVALUO")
    private Long valorAvaluo;
    @Column(name="VALOR_M2_CONSTRUCCION")
    private Double valorM2Construccion;
    @Column(name="VALOR_M2_TERRENO")
    private Double valorM2Terreno;
    @Column(name="VIGENCIA_FORMACION")
    private Long vigenciaFormacion;
    @Column(name="VIGENCIA_SEMESTRE")
    private Long vigenciaSemestre;
    @Column(name="ZONA_POSTAL")
    private Long zonaPostal;

    public Predio() {
    }

    public String getAdicionalDocumento() {
        return adicionalDocumento;
    }

    public void setAdicionalDocumento(String adicionalDocumento) {
        this.adicionalDocumento = adicionalDocumento;
    }

    public Long getAnoResolucionCreacion() {
        return anoResolucionCreacion;
    }

    public void setAnoResolucionCreacion(Long anoResolucionCreacion) {
        this.anoResolucionCreacion = anoResolucionCreacion;
    }

    public Double getAreaComunTerreno() {
        return areaComunTerreno;
    }

    public void setAreaComunTerreno(Double areaComunTerreno) {
        this.areaComunTerreno = areaComunTerreno;
    }

    public Double getAreaConstruida() {
        return areaConstruida;
    }

    public void setAreaConstruida(Double areaConstruida) {
        this.areaConstruida = areaConstruida;
    }

    public Double getAreaPrivadaTerreno() {
        return areaPrivadaTerreno;
    }

    public void setAreaPrivadaTerreno(Double areaPrivadaTerreno) {
        this.areaPrivadaTerreno = areaPrivadaTerreno;
    }

    public Double getAreaTerreno() {
        return areaTerreno;
    }

    public void setAreaTerreno(Double areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public Long getAvaluoAno() {
        return avaluoAno;
    }

    public void setAvaluoAno(Long avaluoAno) {
        this.avaluoAno = avaluoAno;
    }

    public String getCirculo() {
        return circulo;
    }

    public void setCirculo(String circulo) {
        this.circulo = circulo;
    }

    public String getCiudadDocumento() {
        return ciudadDocumento;
    }

    public void setCiudadDocumento(String ciudadDocumento) {
        this.ciudadDocumento = ciudadDocumento;
    }

    public String getClasePredio() {
        return clasePredio;
    }

    public void setClasePredio(String clasePredio) {
        this.clasePredio = clasePredio;
    }

    public String getCodigoDestino() {
        return codigoDestino;
    }

    public void setCodigoDestino(String codigoDestino) {
        this.codigoDestino = codigoDestino;
    }

    public Long getCodigoEstrato() {
        return codigoEstrato;
    }

    public void setCodigoEstrato(Long codigoEstrato) {
        this.codigoEstrato = codigoEstrato;
    }

    public String getCodigoFuente() {
        return codigoFuente;
    }

    public void setCodigoFuente(String codigoFuente) {
        this.codigoFuente = codigoFuente;
    }

    public String getConservacionHistorica() {
        return conservacionHistorica;
    }

    public void setConservacionHistorica(String conservacionHistorica) {
        this.conservacionHistorica = conservacionHistorica;
    }

    public String getDireccionReal() {
        return direccionReal;
    }

    public void setDireccionReal(String direccionReal) {
        this.direccionReal = direccionReal;
    }

    public String getDirecCorrespondencia() {
        return direcCorrespondencia;
    }

    public void setDirecCorrespondencia(String direcCorrespondencia) {
        this.direcCorrespondencia = direcCorrespondencia;
    }

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public String getExistenciaCartografica() {
        return existenciaCartografica;
    }

    public void setExistenciaCartografica(String existenciaCartografica) {
        this.existenciaCartografica = existenciaCartografica;
    }

    public Timestamp getExpiracionDocumento() {
        return expiracionDocumento;
    }

    public void setExpiracionDocumento(Timestamp expiracionDocumento) {
        this.expiracionDocumento = expiracionDocumento;
    }

    public Timestamp getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Timestamp getFechaAutoavaluo() {
        return fechaAutoavaluo;
    }

    public void setFechaAutoavaluo(Timestamp fechaAutoavaluo) {
        this.fechaAutoavaluo = fechaAutoavaluo;
    }

    public Timestamp getFechaAvaluoPrimera() {
        return fechaAvaluoPrimera;
    }

    public void setFechaAvaluoPrimera(Timestamp fechaAvaluoPrimera) {
        this.fechaAvaluoPrimera = fechaAvaluoPrimera;
    }

    public Timestamp getFechaAvaluoSegunda() {
        return fechaAvaluoSegunda;
    }

    public void setFechaAvaluoSegunda(Timestamp fechaAvaluoSegunda) {
        this.fechaAvaluoSegunda = fechaAvaluoSegunda;
    }

    public Timestamp getFechaConservacion() {
        return fechaConservacion;
    }

    public void setFechaConservacion(Timestamp fechaConservacion) {
        this.fechaConservacion = fechaConservacion;
    }

    public Timestamp getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Timestamp fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public Timestamp getFechaIncorporacion() {
        return fechaIncorporacion;
    }

    public void setFechaIncorporacion(Timestamp fechaIncorporacion) {
        this.fechaIncorporacion = fechaIncorporacion;
    }

    public Timestamp getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Timestamp fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public Long getLog() {
        return log;
    }

    public void setLog(Long log) {
        this.log = log;
    }

    public String getMarcaAutoavaluo() {
        return marcaAutoavaluo;
    }

    public void setMarcaAutoavaluo(String marcaAutoavaluo) {
        this.marcaAutoavaluo = marcaAutoavaluo;
    }

    public String getMarcaAvaluoPrimera() {
        return marcaAvaluoPrimera;
    }

    public void setMarcaAvaluoPrimera(String marcaAvaluoPrimera) {
        this.marcaAvaluoPrimera = marcaAvaluoPrimera;
    }

    public String getMarcaAvaluoSegunda() {
        return marcaAvaluoSegunda;
    }

    public void setMarcaAvaluoSegunda(String marcaAvaluoSegunda) {
        this.marcaAvaluoSegunda = marcaAvaluoSegunda;
    }

    public String getMarcaDireccion() {
        return marcaDireccion;
    }

    public void setMarcaDireccion(String marcaDireccion) {
        this.marcaDireccion = marcaDireccion;
    }

    public String getMarcaFormacion() {
        return marcaFormacion;
    }

    public void setMarcaFormacion(String marcaFormacion) {
        this.marcaFormacion = marcaFormacion;
    }

    public String getMarcaPh() {
        return marcaPh;
    }

    public void setMarcaPh(String marcaPh) {
        this.marcaPh = marcaPh;
    }

    public String getMarcaRevision() {
        return marcaRevision;
    }

    public void setMarcaRevision(String marcaRevision) {
        this.marcaRevision = marcaRevision;
    }

    public String getMarcaSectorMal() {
        return marcaSectorMal;
    }

    public void setMarcaSectorMal(String marcaSectorMal) {
        this.marcaSectorMal = marcaSectorMal;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Long getMaxNumPiso() {
        return maxNumPiso;
    }

    public void setMaxNumPiso(Long maxNumPiso) {
        this.maxNumPiso = maxNumPiso;
    }

    public String getNotaria() {
        return notaria;
    }

    public void setNotaria(String notaria) {
        this.notaria = notaria;
    }

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Long getNumeroMejoras() {
        return numeroMejoras;
    }

    public void setNumeroMejoras(Long numeroMejoras) {
        this.numeroMejoras = numeroMejoras;
    }

    public Long getNumeroResolucionCreacion() {
        return numeroResolucionCreacion;
    }

    public void setNumeroResolucionCreacion(Long numeroResolucionCreacion) {
        this.numeroResolucionCreacion = numeroResolucionCreacion;
    }

    public Double getPorcentajeCopropiedad() {
        return porcentajeCopropiedad;
    }

    public void setPorcentajeCopropiedad(Double porcentajeCopropiedad) {
        this.porcentajeCopropiedad = porcentajeCopropiedad;
    }

    public String getTipoMejora() {
        return tipoMejora;
    }

    public void setTipoMejora(String tipoMejora) {
        this.tipoMejora = tipoMejora;
    }

    public String getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(String tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }

    public String getVacante() {
        return vacante;
    }

    public void setVacante(String vacante) {
        this.vacante = vacante;
    }

    public Long getValorAvaluo() {
        return valorAvaluo;
    }

    public void setValorAvaluo(Long valorAvaluo) {
        this.valorAvaluo = valorAvaluo;
    }

    public Double getValorM2Construccion() {
        return valorM2Construccion;
    }

    public void setValorM2Construccion(Double valorM2Construccion) {
        this.valorM2Construccion = valorM2Construccion;
    }

    public Double getValorM2Terreno() {
        return valorM2Terreno;
    }

    public void setValorM2Terreno(Double valorM2Terreno) {
        this.valorM2Terreno = valorM2Terreno;
    }

    public Long getVigenciaFormacion() {
        return vigenciaFormacion;
    }

    public void setVigenciaFormacion(Long vigenciaFormacion) {
        this.vigenciaFormacion = vigenciaFormacion;
    }

    public Long getVigenciaSemestre() {
        return vigenciaSemestre;
    }

    public void setVigenciaSemestre(Long vigenciaSemestre) {
        this.vigenciaSemestre = vigenciaSemestre;
    }

    public Long getZonaPostal() {
        return zonaPostal;
    }

    public void setZonaPostal(Long zonaPostal) {
        this.zonaPostal = zonaPostal;
    }
}
