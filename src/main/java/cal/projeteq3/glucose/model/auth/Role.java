package cal.projeteq3.glucose.model.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role{
	MANAGER("ROLE_MANAGER"),
	EMPLOYER("ROLE_EMPLOYER"),
	STUDENT("ROLE_STUDENT"),
	DIRECTOR("ROLE_DIRECTOR"),
	;

	private final String string;
	private final Set<Role> managedRoles = new HashSet<>();

	static{
		MANAGER.managedRoles.add(EMPLOYER);
		MANAGER.managedRoles.add(STUDENT);
	}

	Role(String string){
		this.string = string;
	}

	public boolean isManagedRole(Role role){
		return managedRoles.contains(role);
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

	public static String getManagedRolesString(Role role){
		StringBuilder stringBuilder = new StringBuilder();
		for(Role validRole : role.managedRoles){
			stringBuilder.append(validRole.name());
			stringBuilder.append(" or ");
		}
		stringBuilder.setLength(stringBuilder.length() - 4);
		return stringBuilder.toString();
	}

	public static List<Role> getManagedRoles(Role role){
		return List.copyOf(role.managedRoles);
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
