package pleb;

public enum ObjectId {
	Player, Block, Obstacle,
	Trampoline, BgObject, FgObject, None,
	Invalid, Coin, Enemy, Effect
}

// player 	— entity which is controlled by user
// Block 		— anything that is static and not harmful
// Obstacle 	— anything that causes damage
// Trampoline 	— Support
// BgObject 	— (background Object) anything that doesn't interact with anything else and runs in game background i.e: clouds
// FgObject	— (foreground Object) anything that interacts with other objects ingame. ie: rain drops
// None 		— "unindentified object"; not declared in config
// Invalid	— Cannot be walked on
// Coin		— object which player collects in order to gain points
// Enemy		— entity which is controlled by computer, danger to player
