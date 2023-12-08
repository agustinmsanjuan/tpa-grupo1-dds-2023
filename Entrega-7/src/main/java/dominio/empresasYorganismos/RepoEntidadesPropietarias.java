package dominio.empresasYorganismos;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RepoEntidadesPropietarias {
    // singleton
    private static RepoEntidadesPropietarias instance = null;

    public static RepoEntidadesPropietarias getInstance(){
        if(instance == null){
            instance = new RepoEntidadesPropietarias();
        }
        return instance;
    }

    List<EntidadPropietaria> entidadesPropietarias = new ArrayList<>();

    public void agregar(EntidadPropietaria entidadPropietaria) {
    //    entidadesPropietarias.add(entidadPropietaria);

        System.out.println("se agregó" + entidadPropietaria.getNombre());
    }

    public List<EntidadPropietaria> getEntidadesPropietarias() {
        return entidadesPropietarias;
    }
}
