package com.telefonica.mshispamjobprivate.users.entity;

import javax.persistence.*;

import com.telefonica.mshispamjobprivate.shared.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "curriculum")
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer idUser;
    private Boolean jDefault;
    private String path;

}
