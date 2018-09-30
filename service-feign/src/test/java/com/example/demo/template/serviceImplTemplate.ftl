import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
public class ${className?cap_first}ServiceImpl implements ${className?cap_first}Service{
	
	@Autowired
	${className?cap_first}Dao ${className}Dao;
    //分页查询
	public List<Map<String,Object>> queryPageList(${className?cap_first}DMO ${className}Dmo){
		  List<Map<String,Object>> list = ${className}Dao.queryPageList(${className}Dmo);
		  return list;	  
	}

	//新增
	public int ${className}Save(${className?cap_first}DMO ${className}Dmo){
		  int result = ${className}Dao.${className}Save(${className}Dmo);
		  return result;
	}
	
	//修改
	public int ${className}Modify(${className?cap_first}DMO ${className}Dmo){
		  int result = ${className}Dao.${className}Modify(${className}Dmo);
		  return result;
	}
	
	//删除
	public int ${className}Dele(${className?cap_first}DMO ${className}Dmo){
		  int result = ${className}Dao.${className}Dele(${className}Dmo);
		  return result;
	}

}
