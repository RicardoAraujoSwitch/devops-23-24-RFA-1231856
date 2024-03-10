package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    @Test
    void testEmployeeCreation() {
        Employee employee = new Employee("John", "Doe", "Description", "Developer");
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("Description", employee.getDescription());
        assertEquals("Developer", employee.getJobTitle());
    }

    @Test
    void testExceptionThrownWhenCreatingEmployeeWithNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(null, "Doe", "Description", "Developer");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", null, "Description", "Developer");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Doe", null, "Developer");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("John", "Doe", "Description", null);
        });
    }

    @Test
    void testEmployeeValidation() {
        Employee employee = new Employee();
        assertFalse(employee.validateArguments(null, "Doe", "Description", "Developer"));
        assertFalse(employee.validateArguments("John", null, "Description", "Developer"));
        assertFalse(employee.validateArguments("John", "Doe", null, "Developer"));
        assertFalse(employee.validateArguments("John", "Doe", "Description", null));
        assertTrue(employee.validateArguments("John", "Doe", "Description", "Developer"));
    }

    @Test
    void testEmployeeEquality() {
        Employee employee1 = new Employee("John", "Doe", "Description", "Developer");
        Employee employee2 = new Employee("John", "Doe", "Description", "Developer");
        assertEquals(employee1, employee2);
    }

    @Test
    void testEmployeeToString() {
        Employee employee = new Employee("John", "Doe", "Description", "Developer");
        String expectedString = "Employee{id=null, firstName='John', lastName='Doe', description='Description', jobTitle='Developer'}";
        assertEquals(expectedString, employee.toString());
    }

    @Test
    void testEmployeeSettersAndGetters() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDescription("Description");
        employee.setJobTitle("Developer");
        assertEquals(1L, employee.getId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("Description", employee.getDescription());
        assertEquals("Developer", employee.getJobTitle());
    }

    @Test
    void testEmployeeHashCode() {
        Employee employee1 = new Employee("John", "Doe", "Description", "Developer");
        Employee employee2 = new Employee("John", "Doe", "Description", "Developer");
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }
}