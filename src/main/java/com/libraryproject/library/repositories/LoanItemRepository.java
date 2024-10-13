package com.libraryproject.library.repositories;

import com.libraryproject.library.entities.LoanItem;
import com.libraryproject.library.entities.pk.LoanItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanItemRepository extends JpaRepository<LoanItem, LoanItemPK> {

}
