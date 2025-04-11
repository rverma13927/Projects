#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <sstream>

struct Row {
    int id;
    std::string name;

    void serialize(std::ostream& out) const {
        out.write(reinterpret_cast<const char*>(&id), sizeof(id));
        size_t len = name.size();
        out.write(reinterpret_cast<const char*>(&len), sizeof(len));
        out.write(name.c_str(), len);
    }

    void deserialize(std::istream& in) {
        in.read(reinterpret_cast<char*>(&id), sizeof(id));
        size_t len;
        in.read(reinterpret_cast<char*>(&len), sizeof(len));
        name.resize(len);
        in.read(&name[0], len);
    }

    void print() const {
        std::cout << "(" << id << ", " << name << ")\n";
    }
};

std::vector<Row> rows;
const std::string DB_FILE = "simple.db";

void load_from_file() {
    std::ifstream in(DB_FILE, std::ios::binary);
    if (!in) return;

    while (in.peek() != EOF) {
        Row row;
        row.deserialize(in);
        rows.push_back(row);
    }
}

void save_to_file() {
    std::ofstream out(DB_FILE, std::ios::binary);
    for (const auto& row : rows) {
        row.serialize(out);
    }
}

void handle_insert(const std::string& input) {
    std::istringstream iss(input);
    std::string cmd;
    int id;
    std::string name;

    iss >> cmd >> id >> name;

    Row row{id, name};
    rows.push_back(row);
    std::cout << "Inserted.\n";
}

void handle_select() {
    for (const auto& row : rows) {
        row.print();
    }
}

int main() {
    std::string input;
    load_from_file();
    std::cout << "MiniDB started. Type 'exit' to quit.\n";

    while (true) {
        std::cout << "db > ";
        std::getline(std::cin, input);

        if (input == "exit") {
            save_to_file();
            std::cout << "Bye!\n";
            break;
        } else if (input.rfind("insert", 0) == 0) {
            handle_insert(input);
        } else if (input == "select") {
            handle_select();
        } else {
            std::cout << "Unrecognized command.\n";
        }
    }

    return 0;
}
