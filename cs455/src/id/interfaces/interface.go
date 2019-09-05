package interfaces

// CreateArgs is used to pass the arguments to an rpc create call.
type CreateArgs struct {
	Username string
	Realname string
	Password [32]byte
	IPAddr   string
	Initial  bool
}

// ModifyArgs is used to pass the arguments to an rpc update call.
type ModifyArgs struct {
	OldUsername string
	NewUsername string
	Password    [32]byte
	IPAddr      string
	Initial     bool
}

// DeleteArgs is used to pass the arguments to an rpc delete call.
type DeleteArgs struct {
	Username string
	Password [32]byte
	Initial  bool
}

// Record contains a user's data.
type Record struct {
	Username  string
	Realname  string
	UUID      string
	CreatedBy string
}

// IDServer interface represents the rpc service.
type IDServer interface {
	Create(args CreateArgs, res *string) error
	Lookup(username string, res *Record) error
	ReverseLookup(id string, res *Record) error
	Modify(args ModifyArgs, res *string) error
	Delete(args DeleteArgs, res *string) error
	GetUsers(args int, res *[]string) error
	GetUUIDS(args int, res *[]string) error
	GetAll(args int, res *[]Record) error
	Ping(args int, res *string) error
}
