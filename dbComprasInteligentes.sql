create database dbComprasInteligentes;

use dbComprasInteligentes;

create table tbFacilitador(
	id int primary key auto_increment,
    facilitador varchar(30) not null
);

create table tbCliente(
	id int primary key auto_increment,
    nombre varchar(30) not null,
    apellido varchar(30) not null,
    direccion varchar(100) not null,
    numeroTelefono char(9) not null
);


create table tbTarjeta(
	id int primary key auto_increment,
    numeroTarjeta char(15) not null,
    fechaExpiracion date not null,
    tipo varchar(10) not null,    
    idFacilitador int not null,
    idCliente int not null,
    index(idFacilitador),
    index(idCliente),
    foreign key(idFacilitador) references tbFacilitador(id) on delete cascade,
	foreign key(idCliente) references tbCliente(id) on delete cascade
);

create table tbCompra(
	id int primary key auto_increment,
    fechaCompra date not null,
    montoTotal numeric(10,2) not null,
    descripcion varchar(50) not null,
    idTarjeta int not null,
    index(idTarjeta),
    foreign key (idTarjeta) references tbTarjeta(id)
);
