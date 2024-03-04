/*
	Author: Elias Neri.
	eliasneri@hotmail.com
 */
package crud.backend.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "manufacturer_base")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufactureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long manufactureId;
    @Column(length = 80)
    private String manufacturerName;
    @Column(name = "manufacturer_cnpj", length = 18)
    private String manufacturerCNPJ;
    @Column(length = 100)
    private String manufacturerFantasyName;
    @Column(length = 80)
    private String manufacturerSocialName;
    private boolean manufacturerActive;
    @Column(length = 40)
    private String manufacturerSite;
    @Column(length = 40)
    private String manufacturerCountry;
    @Column(length = 40)
    private String manufacturerCity;
    @Column(name = "manufacturer_bairro", length = 30)
    private String manufactureNeighbourhood;

}
