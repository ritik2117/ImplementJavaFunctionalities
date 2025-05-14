package streams;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String schemaName = "schemaName";
        String tableName = "tableName";
        String suffix = "dl_test_it";
        String testString = String.format("`%s`.`%s`", schemaName, tableName);;
//        String testString = String.format(schemaName + " %s", "test");
//        String testString = String.format(schemaName + " %s", "test");
//        String testString = String.format("%s" + schemaName + "_%s%s . %s" + tableName + "_%s%s", "`", suffix, "`", "`", suffix, "`");
        System.out.println(testString);

//        List<Employee> employees = Employee.getEmployeeList();
//        List<Employee> sortedEmp = employees.stream()
//                .sorted((emp1, emp2) -> (int) (emp1.getSalary() - emp2.getSalary()))
//                .toList();
//        sortedEmp.stream().map(Employee::getSalary).forEach(System.out::println);
//        The output of this is `schemaName_dl_test_it` . `tableName_dl_test_it`
//        update the code such that output becomes `schemaName` . `tableName`
//        System.out.println(sortedEmp);
    }
}
