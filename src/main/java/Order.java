import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "zamowienie")
public class Order implements Serializable {

    private static final long serialVersionUID = -30740L;

    @Column (name = "id_zamowienia")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_order;

    @Column (name = "cena")
    private float price;

    @Column (name = "nr_zamowienia")
    private int ordinal_number;

    @Column (name = "zreazlizowano")
    private String ifCompleted;

    @ManyToOne
    @JoinColumn (name = "id_prac")
    private Workers workers;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "zawartosc",
            joinColumns = { @JoinColumn(name = "id_zamowienia")},
            inverseJoinColumns = {@JoinColumn(name = "id_czesci")}
    )
    List<CarParts> CarParts = new ArrayList<>();

    public Order(String ifCompleted) {

    }

    public Order(){}

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_zamowienia ) {
        this.id_order = id_zamowienia;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float cena ) {
        this.price = cena;
    }

    public int getOrdinal_number() {
        return ordinal_number;
    }

    public void setOrdinal_number(int nr_zamowienia ) {
        this.ordinal_number = nr_zamowienia;
    }

    public Workers getPracownik () {
        return workers;
    }

    public void setPracownik ( Workers workers) {
        this.workers = workers;
    }

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    public String getIfCompleted() {
        return ifCompleted;
    }

    public void setIfCompleted(String zrealizowano ) {
        this.ifCompleted = zrealizowano;
    }

    public List<CarParts> getCzescSamochodowa () {
        return CarParts;
    }

    public void setCzescSamochodowa ( List<CarParts> carParts) {
        CarParts = carParts;
    }
}
