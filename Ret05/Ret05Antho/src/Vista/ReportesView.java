package Vista;
import Utilidades.*;
import java.sql.*;
import Modelo.dao.*;
import Modelo.vo.ProyectoBancoVo;

public class ReportesView {
    private String repitaCaracter(Character caracter, Integer veces) {
        String respuesta = "";
        for (int i = 0; i < veces; i++) {
        respuesta += caracter;
        }
        return respuesta;
    }
    public void proyectosFinanciadosPorBanco(String banco) {
        System.out.println(repitaCaracter('=', 36) + " LISTADO DE PROYECTOS POR BANCO "
            + repitaCaracter('=', 37));
        if (banco != null && !banco.isBlank()) {
            System.out.println(String.format("%3s %-25s %-20s %-15s %-7s %-30s","ID", "CONSTRUCTORA", "CIUDAD", "CLASIFICACION", "ESTRATO", "LIDER"));
            System.out.println(repitaCaracter('-', 105));
            // TODO Imprimir en pantalla la información del proyecto
            try {
                ProyectoBancoVo.valores(banco);            
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println(e);
            }
    
        }
    }
    public void totalAdeudadoPorProyectosSuperioresALimite(Double limiteInferior) {
            System.out.println(repitaCaracter('=', 1) + " TOTAL DEUDAS POR PROYECTO "
            + repitaCaracter('=', 1));
        if (limiteInferior != null) {
            System.out.println(String.format("%3s %14s", "ID", "VALOR "));
            System.out.println(repitaCaracter('-', 29));
        // TODO Imprimir en pantalla la información del total adeudado
        
        try {
            var conn = JDBCUtilities.getConnection();
            Statement stmt= null;
            ResultSet rs =null;
            String csql="SELECT p.ID_Proyecto, sum(c.Cantidad*mc.Precio_Unidad) as VALOR from Proyecto p join Compra c on (p.ID_Proyecto = c.ID_Proyecto) join MaterialConstruccion mc on(c.ID_MaterialConstruccion = mc.ID_MaterialConstruccion) where c.pagado = 'No' GROUP by p.ID_Proyecto HAVING sum(c.Cantidad*mc.Precio_Unidad) > "+limiteInferior+" order by valor DESC";
        
            stmt=conn.createStatement();
            rs=stmt.executeQuery(csql);

            while (rs.next()) {
                int id =rs.getInt("ID_Proyecto");
                Float valor = rs.getFloat("VALOR");

                System.out.println(String.format("%3d %,15.1f", id, valor));
            }
            rs.close();
            stmt.close();
            conn.close();
        }            
        catch (Exception e) {
            //TODO: handle exception
            System.out.println(e);
        }
    }
    }
    public void lideresQueMasGastan() {
        System.out.println(repitaCaracter('=', 6) + " 10 LIDERES MAS COMPRADORES "
        + repitaCaracter('=', 7));
        System.out.println(String.format("%-25s %14s", "LIDER", "VALOR "));
        System.out.println(repitaCaracter('-', 41));
        // TODO Imprimir en pantalla la información de los lideres   
        try {
            var conn = JDBCUtilities.getConnection();
            Statement stmt= null;
            ResultSet rs =null;
            String csql="SELECT l.Nombre || ' '||l.Primer_Apellido ||' '||l.Segundo_Apellido as LIDER, sum(c.Cantidad*mc.Precio_Unidad) as VALOR from Lider l join Proyecto p on (p.ID_Lider = l.ID_Lider) join Compra c on (p.ID_Proyecto = c.ID_Proyecto) join MaterialConstruccion mc on (C.ID_MaterialConstruccion = mc.ID_MaterialConstruccion) GROUP by LIDER order by VALOR desc LIMIT 10";
            
            stmt=conn.createStatement();
            rs=stmt.executeQuery(csql);
            
            while (rs.next()) {
                String lider= rs.getString("LIDER");
                Float valor = rs.getFloat("VALOR");
                
                System.out.println(String.format("%-25s %,15.1f", lider, valor));
            }
            rs.close();
            stmt.close();
            conn.close();            
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e);
        }
    }
    
}
