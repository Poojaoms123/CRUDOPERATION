package com.example.Quize.Repository;

import com.example.Quize.Model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option,Long> {
}
