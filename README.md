# Car Parts Shop Desktop Application
> Desktop application created to manage shop by workers.

## Table of contents
* [General info](#general-info)
* [Screenshots](#screenshots)
* [Setup](#setup)
* [Code Examples](#code-examples)  
* [Tech/framework used](#techframework-used)  
* [Status](#status)
* [Contact](#contact)
* [License](#license)

## General info
>The project was created by myself as a final paper of subject Object-Oriented Programing(OOP).

## Screenshots

![Example screenshot](src/main/resources/img/1.png)
![Example screenshot](src/main/resources/img/3.png)

## Setup

>Instalation of project is as simple as using it. All you had to do is download it and start with IDE in my case **IntelliJ**.
To start application properly you need also to download database server, mine was [MySQL Community Server](https://dev.mysql.com/downloads/mysql/), and properly configure it.
After configuration you need to create database **carpartsshop**.
Start application twice so it is ready to use.

## Code Examples
Show examples of usage:


                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    CarParts carParts = getTableView().getItems( ).get( getIndex());
                                    CarParts temp = session.get( CarParts.class, carParts.getId_part() );
                                    setText( temp.getKategoria().getCategory_name() );
                                }
                            }
                        };
                        return cell;
                    }
                };

##Tech/framework used

* Java 15
* Hibernate
* CSS
* MYSQL

## Status
Project is: _in progress_ :monocle_face:


## Contact
Created by [@Bartosz Koscielniak](https://github.com/BartoszKoscielniak) - feel free to contact me! :+1:

##License
[MIT](https://choosealicense.com/licenses/mit/) ©

