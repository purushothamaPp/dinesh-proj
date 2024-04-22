package com.creative.hfs.hfsbackend.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RoleTest {

	@Test
	public void testRoleGettersAndSetters() {
		// Create an instance of Role
		Role role = new Role();

		// Set values using setters
		role.setRoleId(1);
		role.setRoleName("Developer");

		// Verify values using getters
		assertEquals(1, role.getRoleId());
		assertEquals("Developer", role.getRoleName());
	}

	@Test
	public void testRoleConstructorWithArgs() {
		// Create an instance of Role using the constructor with arguments
		Role role = new Role(1, "Developer");

		// Verify values using getters
		assertEquals(1, role.getRoleId());
		assertEquals("Developer", role.getRoleName());
	}

	@Test
	public void testRoleNoArgsConstructor() {
		// Create an instance of Role using the no-args constructor
		Role role = new Role();

		// Set values using setters
		role.setRoleId(1);
		role.setRoleName("Developer");

		// Verify values using getters
		assertEquals(1, role.getRoleId());
		assertEquals("Developer", role.getRoleName());
	}
}
