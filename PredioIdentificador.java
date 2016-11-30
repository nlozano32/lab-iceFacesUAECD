package co.gov.uaecd.siic.entities;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Date;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "PREDIO_IDENTIFICADOR", schema = "OPS$SIIC")
@NamedQueries( { @NamedQuery(name = "PredioIdentificador.findAll", query = "select o from PredioIdentificador o")
        , @NamedQuery(name = "PredioIdentificador.getPredios", query = "SELECT p FROM PredioIdentificador p WHERE p.pk.codigoBarrio = :parCodBarrio AND p.pk.codigoManzana = :parCodManzana")
        , @NamedQuery(name = "PredioIdentificador.getPrediosU", query = "SELECT p FROM PredioIdentificador p WHERE p.pk.codigoBarrio = :parCodBarrio")
        , @NamedQuery(name = "PredioIdentificador.getPredioSNChip", query = "SELECT p FROM PredioIdentificador p WHERE p.chip = :parChip ")
        , @NamedQuery(name = "PredioIdentificador.getPredioCS", query = "SELECT p FROM PredioIdentificador p WHERE p.pk.codigoBarrio = :parCodBarrio AND p.pk.codigoManzana = :parCodManzana AND p.pk.codigoPredio = :parCodPredio AND p.pk.codigoConstruccion = :parCodConstruccion AND p.pk.codigoResto = :parCodResto")
        , @NamedQuery(name = "PredioIdentificador.getPredioCodTraductor", query = "SELECT p FROM PredioIdentificador p, Destino d WHERE p.codigoTraductor = :parCodTraductor AND p.destino.codigoDestino = d.codigoDestino ")
        , @NamedQuery(name = "PredioIdentificador.getPredio", query = "SELECT p FROM PredioIdentificador p, Destino d WHERE " + "p.cedulaCatastral = :parCedulaCat AND p.destino.codigoDestino = d.codigoDestino ")
        , @NamedQuery(name = "PredioIdentificador.getPredioSNMatricula", query = "SELECT p FROM PredioIdentificador p, Destino d WHERE p.matricula = :parMatricula AND p.destino.codigoDestino = d.codigoDestino ")
        , @NamedQuery(name = "PredioIdentificador.getPredioLote", query = "SELECT p FROM PredioIdentificador p WHERE p.pk.codigoBarrio = :parCodBarrio AND p.pk.codigoManzana = :parCodManzana AND p.pk.codigoPredio = :parCodPredio")
        } )
@NamedNativeQueries

( { @NamedNativeQuery(name = "PredioIdentificador.getManzanasSector", query = "SELECT DISTINCT(predio.codigo_manzana) FROM ops$siic.predio_identificador predio WHERE predio.codigo_barrio = ? \n" + "  ORDER BY predio.codigo_manzana desc")
        } )
public class PredioIdentificador implements Serializable {
    @EmbeddedId
    private PredioIdentificadorPK pk;

    @Column(name = "CEDULA_CATASTRAL")
    private String cedulaCatastral;

    @Column(name = "CODIGO_TRADUCTOR")
    private String codigoTraductor;

    @Column(name = "MARCA_BORRE")
    private String marcaBorre;

    @Column(name = "CODIGO_DIRECCION")
    private String codigoDireccion;

    private String chip;

    @ManyToOne(cascade = { MERGE, REFRESH })
    @JoinColumn(name = "TIPO_PROPIEDAD")
    private TipoPropiedad tipoPropiedad;
    
    @ManyToOne(cascade = { MERGE, REFRESH })
    @JoinColumn(name = "DOCUMENTO")
    private Documento documento;

    @ManyToOne(cascade = { MERGE, REFRESH })
    @JoinColumn(name = "CODIGO_DESTINO")
    private Destino destino;

    private String matricula;

    @Column(name = "ZONA_POSTAL")
    private int zonaPostal;

    @Column(name = "FECHA_INCORPORACION")
    private Timestamp fechaIncorporacion;

    @Column(name = "AREA_TERRENO")
    private double areaTerreno;

    @Column(name = "VALOR_M2_TERRENO")
    private double valorM2Terreno;

    @Column(name = "VALOR_AVALUO")
    private double valorAvaluo;

    @Column(name = "VIGENCIA_SEMESTRE")
    private int vigenciaSemestre;

    @Column(name = "AVALUO_ANO")
    private double avaluoAno;

    //private int documento;

    @Column(name = "NUMERO_DOCUMENTO")
    private int numeroDocumento;

    private String notaria;

    @Column(name = "FECHA_DOCUMENTO")
    private Timestamp fechaDocumento;

    @Column(name = "CLASE_PREDIO")
    private String clasePredio;

    @Column(name = "CONSERVACION_HISTORICA")
    private String conservacionHistorica;

    @Column(name = "DIRECCION_REAL")
    private String direccionReal;

    @Column(name = "MARCA_DIRECCION")
    private String marcaDireccion;

    @Column(name = "FECHA_ACTUALIZACION")
    private Timestamp fechaActualizacion;

    @Column(name = "FECHA_CONSERVACION")
    private Date fechaConservacion;

    @Column(name = "CODIGO_ESTRATO")
    private int codigoEstrato;

    @Column(name = "CODIGO_FUENTE")
    private String codigoFuente;

    @Column(name = "DIREC_CORRESPONDENCIA")
    private String direcCorrespondencia;

    @Column(name = "TIPO_MEJORA")
    private String tipoMejora;

    @Column(name = "NUMERO_MEJORAS")
    private int numeroMejoras;

    @Column(name = "MARCA_AUTOAVALUO")
    private String marcaAutoavaluo;

    @Column(name = "FECHA_AUTOAVALUO")
    private Timestamp fechaAutoavaluo;

    @Column(name = "MARCA_REVISION")
    private String marcaRevision;

    @Column(name = "FECHA_REVISION")
    private Timestamp fechaRevision;

    @Column(name = "MARCA_AVALUO_PRIMERA")
    private String marcaAvaluoPrimera;

    @Column(name = "FECHA_AVALUO_PRIMERA")
    private Timestamp fechaAvaluoPrimera;

    @Column(name = "MARCA_AVALUO_SEGUNDA")
    private String marcaAvaluoSegunda;

    @Column(name = "FECHA_AVALUO_SEGUNDA")
    private Timestamp fechaAvaluoSegunda;

    @Column(name = "MARCA_SECTOR_MAL")
    private String marcaSectorMal;

    @Column(name = "MARCA_FORMACION")
    private String marcaFormacion;

    @Column(name = "VIGENCIA_FORMACION")
    private int vigenciaFormacion;

    @Column(name = "CIUDAD_DOCUMENTO")
    private String ciudadDocumento;

    @Column(name = "ADICIONAL_DOCUMENTO")
    private String adicionalDocumento;

    @Column(name = "EXPIRACION_DOCUMENTO")
    private Timestamp expiracionDocumento;

    private String circulo;

    private String vacante;

    @Column(name = "AREA_CONSTRUIDA")
    private double areaConstruida;

    @Column(name = "VALOR_M2_CONSTRUCCION")
    private double valorM2Construccion;

    @Column(name = "PORCENTAJE_COPROPIEDAD")
    private double porcentajeCopropiedad;

    @Column(name = "MARCA_PH")
    private String marcaPh;

    @Column(name = "ANO_RESOLUCION_CREACION")
    private int anoResolucionCreacion;

    @Column(name = "NUMERO_RESOLUCION_CREACION")
    private int numeroResolucionCreacion;

    private BigDecimal log;

    @Column(name = "EXISTENCIA_CARTOGRAFICA")
    private String existenciaCartografica;

    @Column(name = "AREA_PRIVADA_TERRENO")
    private double areaPrivadaTerreno;

    @Column(name = "AREA_COMUN_TERRENO")
    private double areaComunTerreno;

    /*@Column(name="MAX_NUM_PISO")
	private int maxNumPiso;*/

    @OneToMany(mappedBy = "predio")
    private List<Calificacion> usos;

    @OneToMany(mappedBy = "predio")
    @OrderBy(value = "numeroPropietario ASC")
    private List<PropietarioPredio> propietarios;

    @OneToMany(mappedBy = "predio")
    @OrderBy(value = "avaluoAno DESC")
    private List<Avaluos> avaluos;

    @OneToMany(mappedBy = "predio")
    private List<Irradiacion> irradiador;

    @OneToMany(mappedBy = "predio")
    @OrderBy(value = "fechaMutacion DESC")
    private List<HistoricoIdentificador> historicoIdentificador;

    @Transient
    private DestinosHacendario destinosHacendario;

    @OneToMany(mappedBy = "predio")
    private List<Boletines> boletines;

    @OneToMany(mappedBy = "predio")
    private List<DireccionSecundaria> dirSecundarias;

    @OneToMany(mappedBy = "predio")
    private List<AvaluosEspeciales> avaluosEspecialesList;
    
    @Transient
    private boolean selected;
    
    @Transient
    private boolean conservadoBloqueado;

    private static final long serialVersionUID = 1L;

    public PredioIdentificador() {
        super();
    }

    public String getCedulaCatastral() {
        return this.cedulaCatastral;
    }

    public void setCedulaCatastral(String cedulaCatastral) {
        this.cedulaCatastral = cedulaCatastral;
    }

    public String getCodigoTraductor() {
        return this.codigoTraductor;
    }

    public void setCodigoTraductor(String codigoTraductor) {
        this.codigoTraductor = codigoTraductor;
    }

    public String getMarcaBorre() {
        return this.marcaBorre;
    }

    public void setMarcaBorre(String marcaBorre) {
        this.marcaBorre = marcaBorre;
    }

    public String getCodigoDireccion() {
        return this.codigoDireccion;
    }

    public void setCodigoDireccion(String codigoDireccion) {
        this.codigoDireccion = codigoDireccion;
    }

    public String getChip() {
        return this.chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getZonaPostal() {
        return this.zonaPostal;
    }

    public void setZonaPostal(int zonaPostal) {
        this.zonaPostal = zonaPostal;
    }

    public Timestamp getFechaIncorporacion() {
        return this.fechaIncorporacion;
    }

    public void setFechaIncorporacion(Timestamp fechaIncorporacion) {
        this.fechaIncorporacion = fechaIncorporacion;
    }

    public double getAreaTerreno() {
        return this.areaTerreno;
    }

    public void setAreaTerreno(double areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public double getValorM2Terreno() {
        return this.valorM2Terreno;
    }

    public void setValorM2Terreno(double valorM2Terreno) {
        this.valorM2Terreno = valorM2Terreno;
    }

    public double getValorAvaluo() {
        return this.valorAvaluo;
    }

    public void setValorAvaluo(double valorAvaluo) {
        this.valorAvaluo = valorAvaluo;
    }

    public int getVigenciaSemestre() {
        return this.vigenciaSemestre;
    }

    public void setVigenciaSemestre(int vigenciaSemestre) {
        this.vigenciaSemestre = vigenciaSemestre;
    }

    public double getAvaluoAno() {
        return this.avaluoAno;
    }

    public void setAvaluoAno(double avaluoAno) {
        this.avaluoAno = avaluoAno;
    }

    /*public int getDocumento() {
        return this.documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }*/

    public int getNumeroDocumento() {
        return this.numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNotaria() {
        return this.notaria;
    }

    public void setNotaria(String notaria) {
        this.notaria = notaria;
    }

    public Timestamp getFechaDocumento() {
        return this.fechaDocumento;
    }

    public void setFechaDocumento(Timestamp fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getClasePredio() {
        return this.clasePredio;
    }

    public void setClasePredio(String clasePredio) {
        this.clasePredio = clasePredio;
    }

    public String getConservacionHistorica() {
        return this.conservacionHistorica;
    }

    public void setConservacionHistorica(String conservacionHistorica) {
        this.conservacionHistorica = conservacionHistorica;
    }

    public String getDireccionReal() {
        return this.direccionReal;
    }

    public void setDireccionReal(String direccionReal) {
        this.direccionReal = direccionReal;
    }

    public String getMarcaDireccion() {
        return this.marcaDireccion;
    }

    public void setMarcaDireccion(String marcaDireccion) {
        this.marcaDireccion = marcaDireccion;
    }

    public Timestamp getFechaActualizacion() {
        return this.fechaActualizacion;
    }

    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaConservacion() {
        return this.fechaConservacion;
    }

    public void setFechaConservacion(Date fechaConservacion) {
        this.fechaConservacion = fechaConservacion;
    }

    public int getCodigoEstrato() {
        return this.codigoEstrato;
    }

    public void setCodigoEstrato(int codigoEstrato) {
        this.codigoEstrato = codigoEstrato;
    }

    public String getCodigoFuente() {
        return this.codigoFuente;
    }

    public void setCodigoFuente(String codigoFuente) {
        this.codigoFuente = codigoFuente;
    }

    public String getDirecCorrespondencia() {
        return this.direcCorrespondencia;
    }

    public void setDirecCorrespondencia(String direcCorrespondencia) {
        this.direcCorrespondencia = direcCorrespondencia;
    }

    public String getTipoMejora() {
        return this.tipoMejora;
    }

    public void setTipoMejora(String tipoMejora) {
        this.tipoMejora = tipoMejora;
    }

    public int getNumeroMejoras() {
        return this.numeroMejoras;
    }

    public void setNumeroMejoras(int numeroMejoras) {
        this.numeroMejoras = numeroMejoras;
    }

    public String getMarcaAutoavaluo() {
        return this.marcaAutoavaluo;
    }

    public void setMarcaAutoavaluo(String marcaAutoavaluo) {
        this.marcaAutoavaluo = marcaAutoavaluo;
    }

    public Timestamp getFechaAutoavaluo() {
        return this.fechaAutoavaluo;
    }

    public void setFechaAutoavaluo(Timestamp fechaAutoavaluo) {
        this.fechaAutoavaluo = fechaAutoavaluo;
    }

    public String getMarcaRevision() {
        return this.marcaRevision;
    }

    public void setMarcaRevision(String marcaRevision) {
        this.marcaRevision = marcaRevision;
    }

    public Timestamp getFechaRevision() {
        return this.fechaRevision;
    }

    public void setFechaRevision(Timestamp fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public String getMarcaAvaluoPrimera() {
        return this.marcaAvaluoPrimera;
    }

    public void setMarcaAvaluoPrimera(String marcaAvaluoPrimera) {
        this.marcaAvaluoPrimera = marcaAvaluoPrimera;
    }

    public Timestamp getFechaAvaluoPrimera() {
        return this.fechaAvaluoPrimera;
    }

    public void setFechaAvaluoPrimera(Timestamp fechaAvaluoPrimera) {
        this.fechaAvaluoPrimera = fechaAvaluoPrimera;
    }

    public String getMarcaAvaluoSegunda() {
        return this.marcaAvaluoSegunda;
    }

    public void setMarcaAvaluoSegunda(String marcaAvaluoSegunda) {
        this.marcaAvaluoSegunda = marcaAvaluoSegunda;
    }

    public Timestamp getFechaAvaluoSegunda() {
        return this.fechaAvaluoSegunda;
    }

    public void setFechaAvaluoSegunda(Timestamp fechaAvaluoSegunda) {
        this.fechaAvaluoSegunda = fechaAvaluoSegunda;
    }

    public String getMarcaSectorMal() {
        return this.marcaSectorMal;
    }

    public void setMarcaSectorMal(String marcaSectorMal) {
        this.marcaSectorMal = marcaSectorMal;
    }

    public String getMarcaFormacion() {
        return this.marcaFormacion;
    }

    public void setMarcaFormacion(String marcaFormacion) {
        this.marcaFormacion = marcaFormacion;
    }

    public int getVigenciaFormacion() {
        return this.vigenciaFormacion;
    }

    public void setVigenciaFormacion(int vigenciaFormacion) {
        this.vigenciaFormacion = vigenciaFormacion;
    }

    public String getCiudadDocumento() {
        return this.ciudadDocumento;
    }

    public void setCiudadDocumento(String ciudadDocumento) {
        this.ciudadDocumento = ciudadDocumento;
    }

    public String getAdicionalDocumento() {
        return this.adicionalDocumento;
    }

    public void setAdicionalDocumento(String adicionalDocumento) {
        this.adicionalDocumento = adicionalDocumento;
    }

    public Timestamp getExpiracionDocumento() {
        return this.expiracionDocumento;
    }

    public void setExpiracionDocumento(Timestamp expiracionDocumento) {
        this.expiracionDocumento = expiracionDocumento;
    }

    public String getCirculo() {
        return this.circulo;
    }

    public void setCirculo(String circulo) {
        this.circulo = circulo;
    }

    public String getVacante() {
        return this.vacante;
    }

    public void setVacante(String vacante) {
        this.vacante = vacante;
    }

    public double getAreaConstruida() {
        return this.areaConstruida;
    }

    public void setAreaConstruida(double areaConstruida) {
        this.areaConstruida = areaConstruida;
    }

    public double getValorM2Construccion() {
        return this.valorM2Construccion;
    }

    public void setValorM2Construccion(double valorM2Construccion) {
        this.valorM2Construccion = valorM2Construccion;
    }

    public double getPorcentajeCopropiedad() {
        return this.porcentajeCopropiedad;
    }

    public void setPorcentajeCopropiedad(double porcentajeCopropiedad) {
        this.porcentajeCopropiedad = porcentajeCopropiedad;
    }

    public String getMarcaPh() {
        return this.marcaPh;
    }

    public void setMarcaPh(String marcaPh) {
        this.marcaPh = marcaPh;
    }

    public int getAnoResolucionCreacion() {
        return this.anoResolucionCreacion;
    }

    public void setAnoResolucionCreacion(int anoResolucionCreacion) {
        this.anoResolucionCreacion = anoResolucionCreacion;
    }

    public int getNumeroResolucionCreacion() {
        return this.numeroResolucionCreacion;
    }

    public void setNumeroResolucionCreacion(int numeroResolucionCreacion) {
        this.numeroResolucionCreacion = numeroResolucionCreacion;
    }

    public BigDecimal getLog() {
        return this.log;
    }

    public void setLog(BigDecimal log) {
        this.log = log;
    }

    public String getExistenciaCartografica() {
        return this.existenciaCartografica;
    }

    public void setExistenciaCartografica(String existenciaCartografica) {
        this.existenciaCartografica = existenciaCartografica;
    }

    public double getAreaPrivadaTerreno() {
        return this.areaPrivadaTerreno;
    }

    public void setAreaPrivadaTerreno(double areaPrivadaTerreno) {
        this.areaPrivadaTerreno = areaPrivadaTerreno;
    }

    public double getAreaComunTerreno() {
        return this.areaComunTerreno;
    }

    public void setAreaComunTerreno(double areaComunTerreno) {
        this.areaComunTerreno = areaComunTerreno;
    }

    /*public int getMaxNumPiso() {
		return this.maxNumPiso;
	}

	public void setMaxNumPiso(int maxNumPiso) {
		this.maxNumPiso = maxNumPiso;
	}*/

    public void setPk(PredioIdentificadorPK pk) {
        this.pk = pk;
    }

    public PredioIdentificadorPK getPk() {
        return pk;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setUsos(List<Calificacion> usos) {
        this.usos = usos;
    }

    public List<Calificacion> getUsos() {
        return usos;
    }

    public void setPropietarios(List<PropietarioPredio> propietarios) {
        this.propietarios = propietarios;
    }

    public List<PropietarioPredio> getPropietarios() {
        return propietarios;
    }

    public void setIrradiador(List<Irradiacion> irradiador) {
        this.irradiador = irradiador;
    }

    public List<Irradiacion> getIrradiador() {
        return irradiador;
    }

    public List<HistoricoIdentificador> getHistoricoIdentificador() {
        return historicoIdentificador;
    }

    public void setHistoricoIdentificador(List<HistoricoIdentificador> historicoIdentificador) {
        this.historicoIdentificador = historicoIdentificador;
    }

    public TipoPropiedad getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(TipoPropiedad tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }

    public DestinosHacendario getDestinosHacendario() {
        return destinosHacendario;
    }

    public void setDestinosHacendario(DestinosHacendario destinosHacendario) {
        this.destinosHacendario = destinosHacendario;
    }

    public List<Avaluos> getAvaluos() {
        return avaluos;
    }

    public void setAvaluos(List<Avaluos> avaluos) {
        this.avaluos = avaluos;
    }

    public void setBoletines(List<Boletines> boletines) {
        this.boletines = boletines;
    }

    public List<Boletines> getBoletines() {
        return boletines;
    }

    public List<DireccionSecundaria> getDirSecundarias() {
        return dirSecundarias;
    }

    public void setDirSecundarias(List<DireccionSecundaria> dirSecundarias) {
        this.dirSecundarias = dirSecundarias;
    }

    public List<HistoricoIdentificador> direccionesAnteriores() {
        List<HistoricoIdentificador> dirAnteriores = new ArrayList<HistoricoIdentificador>();
        if (historicoIdentificador != null) {
            for (HistoricoIdentificador his: historicoIdentificador) {
                if (his.getId().getCodigoMutacion() == 94)
                    dirAnteriores.add(his);
            }

            if (dirAnteriores.size() > 0)
                return dirAnteriores;
            else
                return null;
        }
        else {
            return null;
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setAvaluosEspecialesList(List<AvaluosEspeciales> avaluosEspecialesList) {
        this.avaluosEspecialesList = avaluosEspecialesList;
    }

    public List<AvaluosEspeciales> getAvaluosEspecialesList() {
        return avaluosEspecialesList;
    }

    public AvaluosEspeciales addAvaluosEspeciales(AvaluosEspeciales avaluosEspeciales) {
        getAvaluosEspecialesList().add(avaluosEspeciales);
        avaluosEspeciales.setPredio(this);
        return avaluosEspeciales;
    }

    public AvaluosEspeciales removeAvaluosEspeciales(AvaluosEspeciales avaluosEspeciales) {
        getAvaluosEspecialesList().remove(avaluosEspeciales);
        avaluosEspeciales.setPredio(null);
        return avaluosEspeciales;
    }

    public void setConservadoBloqueado(boolean conservadoBloqueado) {
        this.conservadoBloqueado = conservadoBloqueado;
    }

    public boolean isConservadoBloqueado() {
        return conservadoBloqueado;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Documento getDocumento() {
        return documento;
    }
}
