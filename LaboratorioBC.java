package co.com.sogamoxi.Laboratorio;

import co.com.sogamoxi.Laboratorio.DTOLab.PredioLabDTO;
import co.com.sogamoxi.facadeLaboratorio.FacLaboratorio;
import co.com.sogamoxi.facadeLaboratorio.entities.PredioLab;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public class LaboratorioBC {

    private boolean visibleListPredio = false;
    private String codBarrio;
    private PredioLabDTO predioLabDTO;
    private FacLaboratorio facLaboratorio;
    private List<PredioLabDTO> listPrediosLab;

    public LaboratorioBC() {
    
        
    }
    


    public void setPredioLab(){
    

     try{

        if(facLaboratorio.getPrediosLab(getCodBarrio())!=null){

             facLaboratorio = new FacLaboratorio();
             listPrediosLab = new ArrayList<PredioLabDTO>();

             for(PredioLab preLab: facLaboratorio.getPrediosLab(codBarrio)){

                 predioLabDTO = new PredioLabDTO();

                           predioLabDTO.setCodigoBarrio(preLab.getCodigoBarrio());
                           predioLabDTO.setCodigoManzana(preLab.getCodigoManzana());
                           predioLabDTO.setCodigoPredio(preLab.getCodigoPredio());
                           predioLabDTO.setCodigoConstruccion(preLab.getCodigoConstruccion());
                           predioLabDTO.setCodigoResto(preLab.getCodigoResto());
                           predioLabDTO.setDireccionReal(preLab.getDireccionReal());
                           predioLabDTO.setDirecCorrespondencia(preLab.getDirecCorrespondencia());
                           predioLabDTO.setMatricula(preLab.getMatricula());
                           predioLabDTO.setValorAvaluo(preLab.getValorAvaluo());                                    
                           listPrediosLab.add(predioLabDTO);
                                
                                }
                           }
                   

                    }catch(Exception e){
                        e.printStackTrace();
             }

        }
        
        public String actionBuscarPredio(){
        
            FacesContext context;
            context = FacesContext.getCurrentInstance();
            
            facLaboratorio = new FacLaboratorio();
        
            if(facLaboratorio.getPrediosLab(getCodBarrio()).size()>0){
            
                setPredioLab();
                setVisibleListPredio(true);
                
                
            }else{
            
                setVisibleListPredio(false);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existen predios con este código de barrio", null));
            }
                     
            return "ok";
        }

    public void setVisibleListPredio(boolean visibleListPredio) {
        this.visibleListPredio = visibleListPredio;
    }

    public boolean isVisibleListPredio() {
        return visibleListPredio;
    }

    public void setCodBarrio(String codBarrio) {
        this.codBarrio = codBarrio;
    }

    public String getCodBarrio() {
        return codBarrio;
    }

    public void setPredioLabDTO(PredioLabDTO predioLabDTO) {
        this.predioLabDTO = predioLabDTO;
    }

    public PredioLabDTO getPredioLabDTO() {
        return predioLabDTO;
    }

    public void setFacLaboratorio(FacLaboratorio facLaboratorio) {
        this.facLaboratorio = facLaboratorio;
    }

    public FacLaboratorio getFacLaboratorio() {
        return facLaboratorio;
    }

    public void setListPrediosLab(List<PredioLabDTO> listPrediosLab) {
        this.listPrediosLab = listPrediosLab;
    }

    public List<PredioLabDTO> getListPrediosLab() {
        return listPrediosLab;
    }
}
