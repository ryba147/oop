/*Тарас Вілінський, КН-114
 * Планувальник задач
 * */

#include <iostream>
#include <string>
#include "task.h"
using namespace std;

int main() {
    string str1;
    int option;
    int whichOne;

    do {
        cout << "\n~~~~~~~~~~~~~~~~~~~~~~~~~" << endl;
        cout << "Enter command\n";
        cout << "1. Create task\n";
        cout << "2. Show the list of tasks\n";
        cout << "3. Delete task\n";
        cout << "4. Delete all tasks\n";
        cout << "Other - escape\n";
        cout << "Command: ";

        cin >> option;
        cout << "~~~~~~~~~~~~~~~~~~~~~~~~~" << endl;
        cin.ignore();                                   // getline does not working without cin.ignore

        switch(abs(option)) {
            case 1: {
                bool isImportant;
                taskManage manage;      // створюєтсья об'єкт, який передаватиметься
                getMethod get;             // клас, який надає доступ до методів

                cout << "Priority of task\n" << "\t1. - !\n\t2. - !!!" << endl;
                isImportant = importance();
                cin.ignore();

                /* відбувається запис у файл. залежно від важливості задачі вони записуються у файл мейн.тхт для простих
                 * задач і у файл імпортант.тхт для важливих
                 * */

                cout << "Set a task\n" << "-> ";
                getline(cin, str1);

                get.setTask(&manage, str1, isImportant);       // виклик методів
                break;
            }

            case 2: {
                taskManage manage;      // створюєтсья об'єкт, який передаватиметься
                getMethod maintask;             // клас, який надає доступ до методів

                maintask.getTask(&manage);      // виклик методів
                break;
            }

            /* видалення конкретної задачі */
            case 3: {
                cout << "Which task you want to delete?\n" << "-> ";
                cin >> whichOne;

                taskManage tsk;
                getMethod get;
                get.deleteTask(&tsk, whichOne);
                break;
            }

            /* видалення усіх записаних задач */
            case 4: {
                taskManage manage;      // створюєтсья об'єкт, який передаватиметься
                getMethod get;             // клас, який надає доступ до методів

                get.deleteFile(&manage);
                break;
            }
        }
        if(option > 4) { break; }
    } while(true);
}
