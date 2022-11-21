//Clase abstracta (o base) de la que derivan Centro, Departamento, Puesto y Usuario

package modelo;

public abstract class Elemento {
    public int Id;
    public String Nombre;
    
    public Elemento(int id) {
        this.Id = id;
    }
    
    public void SetId(int id) {
        this.Id = id;
    }
    
    @Override
    public String toString() {
        return Nombre;
    }
}
