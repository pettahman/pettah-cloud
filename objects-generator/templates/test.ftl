package com.p.jackson.${doc.@package};

public class ${doc.@name} {
	
	public ${doc.@name}() {}
	
	public ${doc.@name}(${doc.@name} ${doc.@name?lower_case}) {
		<#list doc.Field as Field>
		this.${Field.@name} = ${doc.@name?lower_case}.get${Field.@name?cap_first}();
		</#list>
	}
	
	<#list doc.Field as Field>
  	private ${Field.@javaType} ${Field.@name};
	</#list>
	
	<#list doc.Field as Field>
  	public ${Field.@javaType} get${Field.@name?cap_first}() {
		return this.${Field.@name};
	}
	</#list>		
	
	<#list doc.Field as Field>
	public void set${Field.@name?cap_first}(${Field.@javaType} ${Field.@name}) {
		this.${Field.@name} = ${Field.@name};
	}
	</#list>
}