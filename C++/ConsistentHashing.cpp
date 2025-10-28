#include <iostream>
#include <map>
#include <set>
#include <string>
#include <functional>
#include <vector>
using namespace std;

class ConsistentHashRing {
private:
    map<size_t, string> ring;  // Only need this!
    int virtualNodesPerNode;
    
    size_t getHash(const string& value) {
        hash<string> hashFunction;
        return hashFunction(value);
    }

public:
    ConsistentHashRing(int replicas = 150) : virtualNodesPerNode(replicas) {}
    
    void addNode(const string& nodeName) {
        for (int i = 0; i < virtualNodesPerNode; ++i) {
            string virtualNodeKey = nodeName + "_vnode_" + to_string(i);
            size_t hashValue = getHash(virtualNodeKey);
            ring[hashValue] = nodeName;  // No need to maintain separate set
        }
    }
    
    void removeNode(const string& nodeName) {
        for (int i = 0; i < virtualNodesPerNode; ++i) {
            string virtualNodeKey = nodeName + "_vnode_" + to_string(i);
            size_t hashValue = getHash(virtualNodeKey);
            ring.erase(hashValue);  // Just erase from map
        }
    }
    
    string getNode(const string& key) {
        if (ring.empty()) {
            return "";
        }
        
        size_t keyHash = getHash(key);
        
        // Use map's lower_bound directly!
        auto it = ring.lower_bound(keyHash);
        
        // Wrap around if past the end
        if (it == ring.end()) {
            it = ring.begin();
        }
        
        return it->second;
    }
     // Display distribution of keys across nodes
    void displayDistribution(const vector<string>& keys) {
        map<string, int> distribution;
        
        for (const auto& key : keys) {
            string node = getNode(key);
            distribution[node]++;
        }
        
        cout << "\nKey Distribution:\n";
        for (const auto& pair : distribution) {
            cout << "Node " << pair.first << ": " << pair.second << " keys\n";
        }
    }
};


int main() {
    // Create a consistent hash ring with 150 virtual nodes per physical node
    ConsistentHashRing hashRing(150);
    
    // Add physical nodes
    hashRing.addNode("Server_A");
    hashRing.addNode("Server_B");
    hashRing.addNode("Server_C");
    hashRing.addNode("Server_D");
    
    cout << "\n--- Testing Key Mapping ---\n";
    
    // Test key lookups
    vector<string> testKeys = {
        "user:1001", "user:1002", "user:1003", "user:1004",
        "session:abc123", "cache:page1", "cache:page2"
    };
    
    for (const auto& key : testKeys) {
        string node = hashRing.getNode(key);
        cout << "Key '" << key << "' -> " << node << "\n";
    }
    
    // Simulate adding a new node
    cout << "\n--- Adding New Node ---\n";
    hashRing.addNode("Server_E");
    
    cout << "\nKey mappings after adding Server_E:\n";
    for (const auto& key : testKeys) {
        string node = hashRing.getNode(key);
        cout << "Key '" << key << "' -> " << node << "\n";
    }
    
    // Simulate removing a node
    cout << "\n--- Removing Node ---\n";
    hashRing.removeNode("Server_B");
    
    cout << "\nKey mappings after removing Server_B:\n";
    for (const auto& key : testKeys) {
        string node = hashRing.getNode(key);
        cout << "Key '" << key << "' -> " << node << "\n";
    }
    
    // Display load distribution
    vector<string> largeKeySet;
    for (int i = 0; i < 10000; ++i) {
        largeKeySet.push_back("key_" + to_string(i));
    }
    hashRing.displayDistribution(largeKeySet);
    
    return 0;
}