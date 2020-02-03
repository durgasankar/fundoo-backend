package com.bridgeLabz.fundooNotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgeLabz.fundooNotes.model.Label;

@Repository
public interface ILabelRepository extends JpaRepository<Label, Long> {

	public Label findOneBylabelName(String name);

}
