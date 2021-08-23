// generated on ${.now?date} at ${.now?time} based on ${.current_template_name}
package demo.generated.controller;

import demo.generated.controller.abs.Abstract${name}Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "${name?uncap_first}")
public class ${name}Controller extends Abstract${name}Controller {
}
