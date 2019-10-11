#include <iostream>
#include <vector>
#include <map>
#include <algorithm>
#include <functional>

using namespace std;

class Neighbour
{
public:
	string from;
	string to;
	int distance = 0;


	Neighbour()
	{
		distance = 0;
	};

	Neighbour(const string& from, const string& to, const int distance) :
		from(from), to(to), distance(distance)
	{
	}

};


class Cost
{
public:
	string name;
	string parent;
	int cost = 0;

	Cost()
	{
		cost = 0;
	}

	Cost(const string& name, const string& parent, const int cost)
		: name(name),
		  parent(parent),
		  cost(cost)
	{
	}
};

unique_ptr<vector<Neighbour>> findAllNeighbour(
	const unique_ptr<vector<Neighbour>>& neighbourVector, 
	const string& from)
{
	auto allNei = unique_ptr<vector<Neighbour>>(new vector<Neighbour>());

	for (auto i = neighbourVector -> begin(); i != neighbourVector -> end(); ++i)
	{
		if(i->from == from)
		{
			allNei->push_back(*i);
		}
	}

	return allNei;
}


bool contains(const unique_ptr<vector<Neighbour>>& neighbourVector, string name)
{
	for (auto i = neighbourVector->begin(); i != neighbourVector->end(); ++i)
	{
		if(i->to == name)
		{
			return true;
		}
	}

	return false;
}


Cost findLeastCostNeighbour(const unique_ptr<map<string, Cost>>& map, const unique_ptr<vector<Neighbour>>& neighbourVector)
{
	Cost* least = nullptr;
	for (auto i = map -> begin(); i != map -> end(); ++i)
	{
		if (contains(neighbourVector, (i->second).name)) 
		{
			if (least == nullptr)
			{
				least = &(i->second);
			}
			else
			{
				if ((i->second).cost < least->cost)
				{
					least = &(i->second);
				}
			}
		}
		
	}
	return *least;
}

int getCost(unique_ptr<map<string, Cost>>& costMap, string to)
{
	if(to == "start")
	{
		return 0;
	} else
	{
		return costMap->find(to)->second.cost;
	}
}

int main()
{
	auto neighbourVector = unique_ptr<vector<Neighbour>>(new vector<Neighbour>());
	neighbourVector->push_back(Neighbour("start", "A", 6));
	neighbourVector->push_back(Neighbour("start", "B", 2));
	neighbourVector->push_back(Neighbour("A", "end", 1));
	neighbourVector->push_back(Neighbour("B", "A", 3));
	neighbourVector->push_back(Neighbour("B", "end", 5));

	auto costMap = unique_ptr<map<string, Cost>>(new map<string, Cost>());
	string from = "start";
	while (true)
	{
		auto allNei = findAllNeighbour(neighbourVector, from);
		for (auto i = allNei->begin(); i != allNei->end(); ++i)
		{
			// not exist
			if (costMap -> find(i -> to) == costMap -> end())
			{
				(*costMap)[i->to] = Cost(i->to, from, getCost(costMap, from) + i -> distance);
			}
			else
			{
				auto cost = costMap -> find(i -> to)->second;

				const auto newCost = i->distance + getCost(costMap, from);
				if (newCost < cost.cost)
				{
					(*costMap)[i->to].cost = newCost;
					(*costMap)[i->to].parent = from;
				}
			}
		}

		from = findLeastCostNeighbour(costMap, allNei).name;
		if (from == "end")
		{
			break;
		}
	}


	string end = "end";
	string path = end;
	while (true)
	{
		end = (*costMap)[end].parent;
		path = path + " -> " + end;
		if (end == "start")
		{
			break;
		}
	}

	cout << path << endl;
	cout << (*costMap)["end"].cost << endl;

}


