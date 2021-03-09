import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "kategoria")
public class Category implements Serializable {

    private static final long serialVersionUID = -30720L;

    @Column (name = "id_kat")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cat;

    @Column (name = "nazwa_kategorii")
    private String category_name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.DETACH)
    private List<CarParts> carPartsList;

    public Category(String category_name) {
        this.category_name = category_name;
    }

    public Category(){}

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_kat ) {
        this.id_cat = id_kat;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String nazwa_kategorii ) {
        this.category_name = nazwa_kategorii;
    }

    public List<CarParts> getCzescSamochodowaList () {
        return carPartsList;
    }

    public void setCzescSamochodowaList ( List<CarParts> carPartsList) {
        this.carPartsList = carPartsList;
    }

    @Override
    public String toString () {
        return "Kategoria{" +
                "id_kat=" + id_cat +
                ", nazwa_kategorii='" + category_name + '\'' +
                ", czescSamochodowaList=" + carPartsList +
                '}';
    }

}
