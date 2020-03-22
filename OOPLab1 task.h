/*Тарас Вілінський, КН-114
 * Планувальник задач
 * */

#include <iostream>
#include <string>
#include <fstream>
#include <vector>
using namespace std;
/* функція важливості задачі. відповідно до вибору користувача повертає 2 значення - тру, якщо задача важлива, фолс -
 * якщо ні
 * */
bool importance() {
    int temp;
    bool importance = false;

    cout << "Choice: ";
    do {
        cin >> temp;
        if (temp - 1 == 1) {
            importance = true;
        }
        if (temp - 1 == 0) {
            importance = false;
        }
    } while(temp > 2 || temp < 0);

    return importance;
}


class parentAbstr {
public:
    virtual void setTask(string &tsk, bool imp) = 0;
    virtual void deleteTask(int whichOne) = 0;
    virtual void getTask() = 0;
    virtual void deleteFile() = 0;

    virtual ~parentAbstr() {
    }
protected:
    string Task;
    bool isEmpty = true;
};

// вказівник на батька, але далі присутнє наслідування
// і перевизначення віртуальних ф-й
class taskManage: public parentAbstr {
public:
    /* constructor */
    taskManage() {
        ifstream inputFile("main.txt");
        if(inputFile.is_open()) {
            this->isEmpty = false;
        } else {
            ifstream impFile("important.txt");
            if(impFile.is_open()) {
                this->isEmpty = false;
            }
            impFile.close();
        }
        inputFile.close();
    }
    /* 1 */
    void setTask(string &tsk, bool imp) override {
        this->isEmpty = false;
        this->Task = tsk;                                  // private string assignment

        ofstream toStream;
        if(imp) {
            toStream.open("important.txt", ios::app);       // writing to file
        } else {
            toStream.open("main.txt", ios::app);
        }
        toStream << Task << endl;
        toStream.close();
    }
    /* 2 */
    void getTask() override {
        if(isEmpty != 1) {
            ifstream inputFile;
            string str;

            inputFile.open("important.txt", ios::in | ios::binary); // read from file

            int i = 1;
            if(inputFile.is_open()) {
                while (!inputFile.eof()) {
                    str = "";
                    getline(inputFile, str);
                    if (str.empty()) {
                        cout << endl;
                        continue;
                    }

                    cout << endl << i << ". " << str;
                    i++;
                }
                inputFile.close();
            }

            /* */
            ifstream impFile;
            string str1;

            impFile.open("main.txt", ios::in | ios::binary); // read from file

            int j = i;
            while (!impFile.eof()) {
                str1 = "";
                getline(impFile, str);
                if(str.empty()) { cout << endl; break; }

                cout << endl << j << ". " << str;
                j++;
            }
            impFile.close();
        } else {
            cout << "!Your task list is empty..." << endl;
        }
    }
    /* 3 */
    void deleteTask(int whichOne) override {
        if(isEmpty != 1) {
            ifstream imp;
            string str1;

            imp.open("important.txt", ios::in | ios::binary);                 // read from file

            int m = 0;
            while (!imp.eof()) {
                str1 = "";
                getline(imp, str1);

                if(str1.empty()) { cout << endl; break; }             // без цієї перевірки, на екран виводиться зайвий порожній таск

                impTaskVector.push_back(str1);
                m++;
            }
            imp.close();

            ifstream inputFile;
            string str;

            inputFile.open("main.txt", ios::in | ios::binary);                 // read from file

            int i = 0;
            while (!inputFile.eof()) {
                str = "";
                getline(inputFile, str);

                if(str.empty()) { cout << endl; break; }             // без цієї перевірки, на екран виводиться зайвий порожній таск

                taskVector.push_back(str);
                i++;
            }
            inputFile.close();

            bool deleteImportant;
            if(impTaskVector.size() < whichOne) {
                deleteImportant = false;
            } else {
                deleteImportant = true;
            }

            if(deleteImportant) {
                int numeration = 0;
                whichOne--;
                if (!impTaskVector.empty()) {
                    impTaskVector.erase(impTaskVector.begin() + whichOne);

                    cout << "\tYour task list a/del:" << endl;                  // after deleting
                    for(unsigned int k = 0; k < impTaskVector.size(); k++) {
                        cout << k + 1 << ". " << impTaskVector[k] << endl;
                        ++numeration;
                    }
                    cout << endl;

                    for(unsigned int k = 0; k < taskVector.size(); k++) {
                        cout << ++numeration << ". " << taskVector[k] << endl;
                    }
                    //
                    // writing the remain tasks to the file
                    //
                    ofstream toStream;
                    toStream.open("important.txt");                      // overwriting the file

                    for(unsigned int j = 0; j < impTaskVector.size(); j++) {
                        toStream << impTaskVector[j] << endl;
                    }
                    toStream.close();
                } else {
                    cout << "!Your task list is already empty..." << endl;
                }
            }

            if(!deleteImportant) {
                int numeration = 0;
                whichOne = (whichOne - (impTaskVector.size()+1)) ;      // +1 is for my vector zero index value erasing

                cout << "WHICH ONE" << whichOne << endl;
                if (!taskVector.empty()) {
                    taskVector.erase(taskVector.begin() + whichOne);

                    cout << "Important" << endl;
                    cout << "\tYour task list a/del:" << endl;                  // after deleting
                    for(unsigned int k = 0; k < impTaskVector.size(); k++) {
                        cout << k + 1 << ". " << impTaskVector[k] << endl;
                        ++numeration;
                    }
                    cout << endl;

                    for(unsigned int k = 0; k < taskVector.size(); k++) {
                        cout << ++numeration << ". " << taskVector[k] << endl;
                    }
                    //
                    // writing the remain tasks to the file
                    //
                    ofstream toStream;
                    toStream.open("main.txt");                      // overwriting the file

                    for(unsigned int j = 0; j < taskVector.size(); j++) {
                        toStream << taskVector[j] << endl;
                    }
                    toStream.close();
                } else {
                    cout << "!Your task list is already empty..." << endl;
                }
            }
        } else {
            cout << "!Your task list is already empty..." << endl;
        }
    }
    /* 4 */
    void deleteFile() override {
        if(!isEmpty) {
            remove("main.txt");
            remove("important.txt");
            cout << "!Successfully deleted" << endl;
            this->isEmpty = true;
        } else {
            cout << "!Nothing to delete" << endl;
        }
    }
private:
    vector<string> taskVector;
    vector<string> impTaskVector;
};
/* */
class getMethod {
public:
    void setTask(parentAbstr *parent, string &str, bool imp) {
        parent->setTask(str, imp);
    }
    void getTask(parentAbstr *parent) {
        parent->getTask();
    }
    void deleteFile(parentAbstr *parent) {
        parent->deleteFile();
    }
    void deleteTask(parentAbstr *parent, int &num) {
        parent->deleteTask(num);
    }
};
