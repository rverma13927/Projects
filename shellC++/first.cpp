void parseInput(const std::string& input, std::vector<std::string>& args) {
    std::istringstream iss(input);
    std::string token;
    while (iss >> token) {
        args.push_back(token);
    }
}

void executeCommand(const std::vector<std::string>& args) {
    // Convert args to a format compatible with execvp
    std::vector<char*> cargs;
    for (const auto& arg : args) {
        cargs.push_back(const_cast<char*>(arg.c_str()));
    }
    cargs.push_back(nullptr);

    // Execute the command
    if (execvp(cargs[0], cargs.data()) == -1) {
        perror("Error executing command");
    }
}


void first(){
std::string input;
    std::vector<std::string> args;

    while (true) {
        std::cout << "myShell> ";
        std::getline(std::cin, input);

        if (input == "exit") {
            break;
        }

        args.clear();
        parseInput(input, args);
        if (args.empty()) {
            continue;
        }

        pid_t pid = fork();
        if (pid == 0) {
            // Child process
            executeCommand(args);
            exit(0);
        } else if (pid > 0) {
            // Parent process
            wait(nullptr);
        } else {
            perror("Fork failed");
        }
    }}
