package cal.projeteq3.glucose.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum Role{
	ADMIN("ROLE_ADMIN"),
	MANAGER("ROLE_MANAGER"),
	EMPLOYER("ROLE_EMPLOYER"),
	STUDENT("ROLE_STUDENT");

	private final String string;
	private final Set<Role> validRoles = new HashSet<>();

	static{
		ADMIN.validRoles.add(MANAGER);
		ADMIN.validRoles.add(EMPLOYER);
		ADMIN.validRoles.add(STUDENT);
		MANAGER.validRoles.add(EMPLOYER);
		MANAGER.validRoles.add(STUDENT);
	}

	Role(String string){
		this.string = string;
	}

	public boolean isManagedRole(Role role){
		return validRoles.contains(role);
	}

	public static boolean contains(Role role){
		for(Role r : Role.values()){
			if(r == role){
				return true;
			}
		}
		return false;
	}

	public static String getAllRolesString(){
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < Role.values().length; i++){
			stringBuilder.append(Role.values()[i].name());
			if(i != Role.values().length - 1){
				stringBuilder.append(" or ");
			}
		}
		return stringBuilder.toString();
	}

	public static String getAllowedRolesString(Role role){
		StringBuilder stringBuilder = new StringBuilder();
		for(Role validRole : role.validRoles){
			stringBuilder.append(validRole.name());
			stringBuilder.append(" or ");
		}
		stringBuilder.setLength(stringBuilder.length() - 4);
		return stringBuilder.toString();
	}

	public static List<Role> getAllowedRoles(Role role){
		return List.copyOf(role.validRoles);
	}

	public static Role fromString(String string){
		for(Role role : Role.values()){
			if(role.string.equalsIgnoreCase(string)){
				return role;
			}
		}
		return null;
	}

	@Override
	public String toString(){
		return string;
	}

}
