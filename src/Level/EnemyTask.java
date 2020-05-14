package Level;

import Entity.EnemyController;

/**
 *
 * @author Gama
 */
public class EnemyTask implements Runnable{
    
    EnemyController enemies;
    
    public EnemyTask(EnemyController enemies){
        this.enemies = enemies;
    }
    
    @Override
        public void run(){
            enemies.automaticMovement();
        }
}
