package org.example;


    public class Employee {

        int id;
        String name;
        double salary;
    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

        @Override
        public String toString() {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", salary=" + salary +
                    '}';
        }
}
