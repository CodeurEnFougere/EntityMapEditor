package game.modele.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import game.modele.entity.Player.Player;
import game.modele.entity.living.monster.Zombie;
import game.modele.entity.tileEntity.TikiTorchSmall;
import game.modele.utils.Coordonnees;
import game.modele.utils.Direction;

public class EntityFactory {

	public static Entity create(String id, String param) {
		String[] params = param.split(",");
		Entity e = null;
		try {
			switch(id) {
			case "Player":
				e = (Player) Player.class.getConstructors()[0]
						.newInstance(castParams(Player.class.getConstructors()[0],params));
				break;
			case "TileEntityTP":
				e = new EntityTP(new Coordonnees(Double.parseDouble(params[0]), Double.parseDouble(params[1])), new Direction(Integer.parseInt(params[2])), Boolean.parseBoolean(params[3]), params[4], new Coordonnees(Double.parseDouble(params[5]), Double.parseDouble(params[6])));
				break;
			case "TikiTorchSmall":
				e = (TikiTorchSmall) TikiTorchSmall.class.getConstructors()[0]
						.newInstance(castParams(TikiTorchSmall.class.getConstructors()[0],params));
				break;
			case "Zombie":
				e = (Entity) Zombie.class.getConstructors()[0]
						.newInstance(castParams(Zombie.class.getConstructors()[0],params));
				break;
			default :


			}
		}catch(IllegalAccessException 
				|InstantiationException 
				|IllegalArgumentException 
				|InvocationTargetException 
				|SecurityException e1) {
			e1.printStackTrace();
		}
		return e;
	}
	public static Object[] castParams(Constructor<?> c,String[] parameters){

		String[] params = parameters;
		Object[] os = new Object[c.getParameterCount()];

		int nb = 0;


		for(int i = 0; i < os.length;i++) {			
			Class<?> o = c.getParameterTypes()[i];
			if(o.getName().equals(Coordonnees.class.getName())) {
				os[i] = new Coordonnees(
						Double.parseDouble(params[nb++]), Double.parseDouble(params[nb++]));
			}else if(o.getName().equals(Direction.class.getName())) {
				os[i] = new Direction(
						Integer.parseInt(params[nb++]));
			}else if(o.getName().equals(boolean.class.getName())) {
				os[i] =	Boolean.parseBoolean(params[nb++]);
			}else if(o.getName().equals(String.class.getName())) {
				os[i] = params[nb++]; 
			}else if(o.getName().equals(int.class.getName())) {
				os[i] = Integer.parseInt(params[nb++]);
			}

		}
		return os;

	}}
