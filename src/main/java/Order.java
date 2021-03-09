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

    public Order(){}

    public int getId_order() {
        return id_order;
    }

    public Workers getWorker() {
        return workers;
    }

    public void setWorker(Workers workers) {
        this.workers = workers;
    }

    public String getIfCompleted() {
        return ifCompleted;
    }

    public void setIfCompleted(String zrealizowano ) {
        this.ifCompleted = zrealizowano;
    }

    public List<CarParts> getCarParts() {
        return CarParts;
    }

    public void setCarParts(List<CarParts> carParts) {
        CarParts = carParts;
    }
}
