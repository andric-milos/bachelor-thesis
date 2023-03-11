-- user roles
insert into role (id, name) values (1, 'ROLE_PLAYER');
insert into role (id, name) values (2, 'ROLE_MANAGER');

-- users (all passwords are '1234')
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (1, 'Player', 'andric8@gmail.com', 'Milos', 'Andric', '$2a$10$k4jUJpPM21QUXtStVe2sc.Nfcb4yk109QpmnVgS68UYgPZAPAHgyO',
'060123456', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (2, 'Player', 'johndoe@gmail.com', 'John', 'Doe', '$2a$10$QWuGMxNtETK4MgiJUxeJsOXWkR2hlDpB.D3u6/ZZ/lC2L5ywInIE2',
'060111111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (3, 'Player', 'travolta@gmail.com', 'John', 'Travolta', '$2a$10$H8Pn9jUFBnWQlSNk1BbrA.eZx2K81NzHqtDcJuiS.oEb9a4mmpHmq',
'060112111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (4, 'Player', 'scholes@gmail.com', 'Paul', 'Scholes', '$2a$10$4.2KNNsl4pAEzNbM27juQe80L4Q3kYDOe0MeqzICMy07nWia7OgXS',
'060111151', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (5, 'Player', 'benzema@gmail.com', 'Karim', 'Benzema', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'060114111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (6, 'Player', 'kdb@gmail.com', 'Kevin', 'De Bruyne', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'065001111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (7, 'Player', 'lukamodric@gmail.com', 'Luka', 'Modrić', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'069872111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (8, 'Player', 'ppetrovic@gmail.com', 'Petar', 'Petrovic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'063411151', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (9, 'Player', 'markovic@gmail.com', 'Marko', 'Markovic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'063234111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (10, 'Player', 'mitar@gmail.com', 'Mitar', 'Mitrovic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'062224111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (11, 'Player', 'mitrovic@gmail.com', 'Aleksandar', 'Mitrovic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'060344111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (12, 'Player', 'sesko@gmail.com', 'Benjamin', 'Sesko', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'062134111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (13, 'Player', 'petarpopovic@gmail.com', 'Petar', 'Popovic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'062123111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (14, 'Player', 'nikolic@gmail.com', 'Nikola', 'Nikolic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'060134156', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (15, 'Player', 'apesic@gmail.com', 'Aleksandar', 'Pesic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'060114159', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (16, 'Player', 'nstojanovic@gmail.com', 'Nikola', 'Stojanovic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'060114196', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (17, 'Player', 'vukvuk@gmail.com', 'Vuk', 'Vukovic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'060114191', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (18, 'Player', 'vpantic@gmail.com', 'Vladimir', 'Pantic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'066114541', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (19, 'Player', 'jovanjovanovic@gmail.com', 'Jovan', 'Jovanovic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'062134111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (20, 'Player', 'jvukotic@gmail.com', 'Jovan', 'Vukotic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'062114111', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (21, 'Player', 'petarpantic@gmail.com', 'Petar', 'Pantic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'060114145', 0, 0, 0, 0, 0, 0, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (22, 'Manager', 'ninapetkovic@gmail.com', 'Nina', 'Petkovic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'067341145', null, null, null, null, null, null, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (23, 'Manager', 'marijana@gmail.com', 'Marijana', 'Marijanovic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'060888145', null, null, null, null, null, null, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (24, 'Manager', 'marinkovicm@gmail.com', 'Marko', 'Marinkovic', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'060888123', null, null, null, null, null, null, null);
insert into users (id, dtype, email, first_name, last_name, password, telephone, canceled_just_before,
goals_per_appointment, number_of_games, number_of_games_won, number_of_goals, winning_percentage, sports_facility_id)
values (25, 'Manager', 'andric10@gmail.com', 'Miloš', 'Andrić', '$2a$10$hkwefDK7G6uUGeKYzBX2AexSLJWxNrC/yO2F9q0Psv6weYuBA.sBu',
'06084369', null, null, null, null, null, null, null);

-- users_roles
insert into users_roles (user_id, roles_id) values (1, 1);
insert into users_roles (user_id, roles_id) values (2, 1);
insert into users_roles (user_id, roles_id) values (3, 1);
insert into users_roles (user_id, roles_id) values (4, 1);
insert into users_roles (user_id, roles_id) values (5, 1);
insert into users_roles (user_id, roles_id) values (6, 1);
insert into users_roles (user_id, roles_id) values (7, 1);
insert into users_roles (user_id, roles_id) values (8, 1);
insert into users_roles (user_id, roles_id) values (9, 1);
insert into users_roles (user_id, roles_id) values (10, 1);
insert into users_roles (user_id, roles_id) values (11, 1);
insert into users_roles (user_id, roles_id) values (12, 1);
insert into users_roles (user_id, roles_id) values (13, 1);
insert into users_roles (user_id, roles_id) values (14, 1);
insert into users_roles (user_id, roles_id) values (15, 1);
insert into users_roles (user_id, roles_id) values (16, 1);
insert into users_roles (user_id, roles_id) values (17, 1);
insert into users_roles (user_id, roles_id) values (18, 1);
insert into users_roles (user_id, roles_id) values (19, 1);
insert into users_roles (user_id, roles_id) values (20, 1);
insert into users_roles (user_id, roles_id) values (21, 1);
insert into users_roles (user_id, roles_id) values (22, 2);
insert into users_roles (user_id, roles_id) values (23, 2);
insert into users_roles (user_id, roles_id) values (24, 2);
insert into users_roles (user_id, roles_id) values (25, 2);

-- groups
insert into groups_of_players (id, name) values (1, 'Fudbal četvrtkom u 20h');
insert into groups_of_players (id, name) values (2, 'Joga Bonito');

-- adding players to the groups
insert into group_players (group_id, player_id) values (1, 1); -- andric8
insert into group_players (group_id, player_id) values (1, 18); -- vpantic
insert into group_players (group_id, player_id) values (1, 17); -- vukvuk
insert into group_players (group_id, player_id) values (1, 13); -- petarpopovic
insert into group_players (group_id, player_id) values (1, 3); -- travolta
insert into group_players (group_id, player_id) values (1, 4); -- scholes
insert into group_players (group_id, player_id) values (1, 5); -- benzema
insert into group_players (group_id, player_id) values (1, 20); -- jvukotic
insert into group_players (group_id, player_id) values (2, 2); -- johndoe
insert into group_players (group_id, player_id) values (2, 1); -- andric8
insert into group_players (group_id, player_id) values (2, 14); -- nikolic
insert into group_players (group_id, player_id) values (2, 9); -- markovic

-- appointments
insert into locations (id, address, latitude, longitude) values (1, 'Hajduk Veljkova 4', 0.0, 0.0);
insert into appointments (id, capacity, date, occupancy, price, privacy, group_id, location_id) values
(1, 10, '2022-12-15 20:00:00.000000', 0, 3800.00, 0, 2, 1);
insert into locations (id, address, latitude, longitude) values (2, 'Šafarikova 10', 0.0, 0.0);
insert into appointments (id, capacity, date, occupancy, price, privacy, group_id, location_id) values
(2, 10, '2022-12-15 20:00:00.000000', 0, 3800.00, 0, 1, 2);
insert into locations (id, address, latitude, longitude) values (3, 'Bulevara Cara Lazara 83', 0.0, 0.0);
insert into appointments (id, capacity, date, occupancy, price, privacy, group_id, location_id) values
(3, 12, '2022-12-10 20:00:00.000000', 0, 4000.00, 1, 2, 3);
insert into locations (id, address, latitude, longitude) values (4, 'Braće Ribnikar 7', 0.0, 0.0);
insert into appointments (id, capacity, date, occupancy, price, privacy, group_id, location_id) values
(4, 12, '2022-12-22 20:00:00.000000', 0, 3000.00, 1, 1, 4);
insert into locations (id, address, latitude, longitude) values (5, 'Mičurinova 12', 0.0, 0.0);
insert into appointments (id, capacity, date, occupancy, price, privacy, group_id, location_id) values
(5, 12, '2022-12-29 20:00:00.000000', 0, 3400.00, 1, 1, 5);
insert into locations (id, address, latitude, longitude) values (6, 'Bulevar Evrope 78', 0.0, 0.0);
insert into appointments (id, capacity, date, occupancy, price, privacy, group_id, location_id) values
(6, 12, '2022-12-29 20:00:00.000000', 0, 3400.00, 1, 2, 6);