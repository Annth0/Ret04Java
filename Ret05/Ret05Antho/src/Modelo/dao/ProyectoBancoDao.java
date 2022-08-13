package Modelo.dao;
import java.sql.*;
import Utilidades.*;

public class ProyectoBancoDao {
   public static ResultSet consulta(String banco){
    Statement stmt= null;
    ResultSet rs =null; 
    
    try {
        var conn = JDBCUtilities.getConnection();
        
        String csql="select p.ID_Proyecto as ID, p.Constructora, p.Ciudad, p.Clasificacion, t.Estrato, l.Nombre || ' '||l.Primer_Apellido ||' '||l.Segundo_Apellido as LIDER from Proyecto p join Tipo t on(p.ID_Tipo = t.ID_Tipo) join Lider l on(p.ID_Lider = l.ID_Lider) where p.Banco_Vinculado = 'Conavi' order by p.Fecha_Inicio DESC, p.Ciudad, p.Constructora;";
    
        stmt=conn.createStatement();
        rs=stmt.executeQuery(csql);

        //rs.close();
        //stmt.close();
        //conn.close();        
        return rs;
    } catch (Exception e) {
        //TODO: handle exception
        System.out.println(e);
    }
    return rs;
   }
}
