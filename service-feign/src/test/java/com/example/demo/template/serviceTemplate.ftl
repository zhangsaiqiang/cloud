import java.util.List;
import java.util.Map;
public interface ${className?cap_first}Service {
    //分页查询
	List<Map<String,Object>> queryPageList(${className?cap_first}DMO ${className}Dmo);

	//新增
	int ${className}Save(${className?cap_first}DMO ${className}Dmo);
	
	//修改
	int ${className}Modify(${className?cap_first}DMO ${className}Dmo);
	
	//删除
	int ${className}Dele(${className?cap_first}DMO ${className}Dmo);

}
