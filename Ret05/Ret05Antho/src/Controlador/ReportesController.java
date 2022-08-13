package Controlador;
import Vista.ReportesView;
public class ReportesController {
    //llama a la vista

    public static  void main(String[] args) throws Exception{
        var reportesView = new ReportesView();
        var banco = "Conavi";
        reportesView.proyectosFinanciadosPorBanco(banco);
        
    }
    //Vista llama al banco
}
