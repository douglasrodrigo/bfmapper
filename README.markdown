java bean converter
=========================================

usage:

writing bean mapper rules

public class AlunoMappingRules implements RulesMapper {

	@Override
	public void loadRules() {
		MappingRules.addRule(new Converter(StudentA.class, Student.class) {{
			// not necessary to put equals name properties
			add("age", "age");
			//custom transformers
			add("birthday", "birthday").with(new DateTimeTransformer("ddMMyyyy"));
			//default values
			addDefault(Student.class, "gender", Gender.MALE);
			//chained values
			add("address", "address.address1");
		}});
	}
}


converting objects:

Student student = new Mapping().apply(studentA).to(Student.class);

multiple applies

Student student = new Mapping().apply(studentA).apply(carMappedPrevioslyToStudent).to(Student.class);

