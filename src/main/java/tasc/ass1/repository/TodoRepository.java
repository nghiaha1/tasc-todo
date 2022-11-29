package tasc.ass1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tasc.ass1.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String>, PagingAndSortingRepository<Todo, String> {
}
