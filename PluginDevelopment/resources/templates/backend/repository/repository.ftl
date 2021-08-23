// generated on ${.now?date} at ${.now?time} based on ${.current_template_name}
package demo.generated.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import demo.generated.model.${name};
import java.util.List;

@Repository
public interface ${name}Repository extends JpaRepository<${name}, ${keyType?cap_first}> {}