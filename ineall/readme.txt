I. Installing with actualized MANIFEST.FM files

	By default MANIFEST.FM files don't contains the correct SVN rev numbers, because this feature expects to every local changes
	should be commited into svn. To install package with a CORRECT MANIFEST.FM set the doCheck property to true:
	
	ineall$ mvn clean install -Dmaven.buildNumber.doCheck=true

II. Setting version
	1.How to use versions?
		-every ine dependency's version
			${project.version} 
			
		-other dependency's version (using ineall's properties)
			${gwtVersion}
			${ginVersion}
			...
	
	2.How to update ineall's version?
		-check out all modules
		-check out ineall too
		-open console, go to ineall's folder, and type:
		
	ineall$ mvn versions:set -DnewVersion=1.0.18 	#setting parent's and children's version
	 
	ineall$ mvn versions:commit 					#deleting backup files
		