--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2024-08-04 22:09:50

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3385 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 222 (class 1255 OID 423344)
-- Name: role_insert(character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.role_insert(IN "Role" character varying)
    LANGUAGE sql
    AS $$
INSERT INTO public.roles (id, created_date, deleted_date, updated_date, name)
 VALUES (gen_random_uuid(), now(),  null, null, "Role");
$$;


ALTER PROCEDURE public.role_insert(IN "Role" character varying) OWNER TO postgres;

--
-- TOC entry 223 (class 1255 OID 423345)
-- Name: user_delete(uuid); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.user_delete(IN "ID" uuid)
    LANGUAGE sql
    AS $$
update users  set deleted_date = now() where id="ID";
$$;


ALTER PROCEDURE public.user_delete(IN "ID" uuid) OWNER TO postgres;

--
-- TOC entry 224 (class 1255 OID 423346)
-- Name: user_insert(character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.user_insert(IN "Role" character varying)
    LANGUAGE sql
    AS $$
INSERT INTO public.roles (id, created_date, deleted_date, updated_date, name)
 VALUES (gen_random_uuid(), now(),  null, null, "Role");
$$;


ALTER PROCEDURE public.user_insert(IN "Role" character varying) OWNER TO postgres;

--
-- TOC entry 225 (class 1255 OID 423347)
-- Name: user_insert(character varying, character varying, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.user_insert(IN "Email_address" character varying, IN "Username" character varying, IN "Password" character varying)
    LANGUAGE sql
    AS $$
INSERT INTO public.users (id, email_addres , username , "password" , created_date, deleted_date, updated_date)
 VALUES (gen_random_uuid(), "Email_address", "Username", "Password", now(),  null, null);
$$;


ALTER PROCEDURE public.user_insert(IN "Email_address" character varying, IN "Username" character varying, IN "Password" character varying) OWNER TO postgres;

--
-- TOC entry 226 (class 1255 OID 423348)
-- Name: user_update(uuid, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.user_update(IN "ID" uuid, IN "NewPassword" character varying)
    LANGUAGE sql
    AS $$
update users  set password = "NewPassword", updated_date = now() where id="ID";
$$;


ALTER PROCEDURE public.user_update(IN "ID" uuid, IN "NewPassword" character varying) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 423349)
-- Name: merchant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.merchant (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    location character varying(255),
    name character varying(255),
    opened boolean NOT NULL
);


ALTER TABLE public.merchant OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 423354)
-- Name: order_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_detail (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    quantity integer,
    total_price double precision,
    order_id uuid,
    product_id uuid
);


ALTER TABLE public.order_detail OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 423357)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    destination character varying(255),
    order_time timestamp(6) without time zone,
    status character varying(255),
    user_id uuid,
    CONSTRAINT orders_status_check CHECK (((status)::text = ANY (ARRAY[('SELESAI'::character varying)::text, ('BELUM'::character varying)::text, ('PROSES'::character varying)::text, ('BATAL'::character varying)::text])))
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 423363)
-- Name: otp; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.otp (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    email_address character varying(255),
    expired_time timestamp(6) without time zone,
    otp character varying(255)
);


ALTER TABLE public.otp OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 423368)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    category character varying(255),
    name character varying(255),
    price double precision,
    stock integer NOT NULL,
    merchant_id uuid,
    CONSTRAINT product_category_check CHECK (((category)::text = ANY (ARRAY[('BAVERAGES'::character varying)::text, ('DESSERTS'::character varying)::text, ('FOODS'::character varying)::text])))
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 423374)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id uuid NOT NULL,
    name character varying(255),
    CONSTRAINT roles_name_check CHECK (((name)::text = ANY (ARRAY[('ROLE_USER'::character varying)::text, ('ROLE_MERCHANT'::character varying)::text])))
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 423378)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    user_id uuid NOT NULL,
    role_id uuid NOT NULL
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 423381)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    email_address character varying(255),
    password character varying(255),
    username character varying(255),
    verified boolean NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 3372 (class 0 OID 423349)
-- Dependencies: 214
-- Data for Name: merchant; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.merchant (id, created_date, deleted_date, updated_date, location, name, opened) FROM stdin;
bf18a6c4-310e-4757-b110-31b03e075b8d	2024-08-03 14:35:49.459	\N	2024-08-03 14:35:49.459	jl. jakarta	mie gacoan	f
\.


--
-- TOC entry 3373 (class 0 OID 423354)
-- Dependencies: 215
-- Data for Name: order_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_detail (id, created_date, deleted_date, updated_date, quantity, total_price, order_id, product_id) FROM stdin;
e556d55a-9fde-44a7-85ec-9235100608a6	2024-08-03 14:40:10.098	2024-08-03 14:40:48.082944	2024-08-03 14:40:10.098	6	54000	cb80f8b9-b2da-476c-bab5-447c314f101a	f37f2a5d-e186-4bd0-b469-cea292592e9f
3ea1bcaa-6cd0-4345-8770-ca184b6f5c8c	2024-08-03 18:17:14.593	\N	2024-08-03 18:17:14.593	6	54000	bffd4f27-9b00-4f46-ae1c-69220deb63ae	f37f2a5d-e186-4bd0-b469-cea292592e9f
b2288bb9-6d40-4f54-992e-2f4975f7c407	2024-08-03 18:34:07.05	\N	2024-08-03 18:34:07.05	6	54000	cb80f8b9-b2da-476c-bab5-447c314f101a	f37f2a5d-e186-4bd0-b469-cea292592e9f
59090f78-0d0f-4251-8bed-c863a5da36b1	2024-08-03 18:37:51.176	\N	2024-08-03 18:37:51.176	6	54000	98548d59-4ecb-42d2-9628-0ec3ba0ad6b9	f37f2a5d-e186-4bd0-b469-cea292592e9f
221a5c0d-e5bf-4027-845a-54aef0cef901	2024-08-03 18:38:10.089	\N	2024-08-03 18:38:10.089	6	90000	98548d59-4ecb-42d2-9628-0ec3ba0ad6b9	9a28f5b3-0386-4d05-a613-2ebebc36a5a5
\.


--
-- TOC entry 3374 (class 0 OID 423357)
-- Dependencies: 216
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders (id, created_date, deleted_date, updated_date, destination, order_time, status, user_id) FROM stdin;
bffd4f27-9b00-4f46-ae1c-69220deb63ae	2024-08-03 18:16:32.852	\N	2024-08-03 18:32:29.969	Soekarno Hatta	2024-08-03 18:16:32.800704	SELESAI	15261310-5051-42ba-afee-b593abdc466f
cb80f8b9-b2da-476c-bab5-447c314f101a	2024-08-03 14:37:59.21	\N	2024-08-03 18:34:32.586	Soekarno Hatta	2024-08-03 14:37:59.210709	BATAL	15261310-5051-42ba-afee-b593abdc466f
98548d59-4ecb-42d2-9628-0ec3ba0ad6b9	2024-08-03 18:37:35.686	\N	2024-08-03 18:39:29.931	Soekarno Hatta	2024-08-03 18:37:35.67048	BATAL	15261310-5051-42ba-afee-b593abdc466f
\.


--
-- TOC entry 3375 (class 0 OID 423363)
-- Dependencies: 217
-- Data for Name: otp; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.otp (id, created_date, deleted_date, updated_date, email_address, expired_time, otp) FROM stdin;
7d065394-3889-4a85-bdf9-b13db6ee68a2	2024-08-03 14:34:52.515	\N	2024-08-03 14:34:52.515	yunnjahe@gmail.com	2024-08-03 14:39:52.515966	566110
7b09f843-b337-49d0-9df3-bd770f66f742	2024-08-03 14:35:15.003	\N	2024-08-03 14:35:15.003	khrnnisak07@gmail.com	2024-08-03 14:40:15.003991	45598
\.


--
-- TOC entry 3376 (class 0 OID 423368)
-- Dependencies: 218
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (id, created_date, deleted_date, updated_date, category, name, price, stock, merchant_id) FROM stdin;
f37f2a5d-e186-4bd0-b469-cea292592e9f	2024-08-03 14:36:35.738	\N	2024-08-03 18:39:29.931	FOODS	Udang keju	9000	17	bf18a6c4-310e-4757-b110-31b03e075b8d
9a28f5b3-0386-4d05-a613-2ebebc36a5a5	2024-08-03 14:36:47.252	\N	2024-08-03 18:39:29.946	FOODS	Mi Gacoan	15000	23	bf18a6c4-310e-4757-b110-31b03e075b8d
\.


--
-- TOC entry 3377 (class 0 OID 423374)
-- Dependencies: 219
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
0356ba6e-692b-4dc4-a719-cecd5ddbddc0	ROLE_USER
f5a90fb4-262d-42f4-ba57-5cce346d8d97	ROLE_MERCHANT
\.


--
-- TOC entry 3378 (class 0 OID 423378)
-- Dependencies: 220
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_roles (user_id, role_id) FROM stdin;
15261310-5051-42ba-afee-b593abdc466f	0356ba6e-692b-4dc4-a719-cecd5ddbddc0
cd521702-44e9-4357-9081-01083722dd92	f5a90fb4-262d-42f4-ba57-5cce346d8d97
\.


--
-- TOC entry 3379 (class 0 OID 423381)
-- Dependencies: 221
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, created_date, deleted_date, updated_date, email_address, password, username, verified) FROM stdin;
15261310-5051-42ba-afee-b593abdc466f	2024-08-03 14:34:52.366	\N	2024-08-03 14:34:52.366	yunnjahe@gmail.com	$2a$10$Zu17XY9rpYmwqNXElWbT4OgxFbBzjNP0gv3yq8lekQiGk2Qum3TXK	khai	f
cd521702-44e9-4357-9081-01083722dd92	2024-08-03 14:35:15.003	\N	2024-08-03 14:35:15.003	khrnnisak07@gmail.com	$2a$10$y5OlDMMXF9sOc3f9eC3FRe4AZ1BnB4..WrDfqy2owBXEWrU29nz8i	gacoan	f
\.


--
-- TOC entry 3209 (class 2606 OID 423387)
-- Name: merchant merchant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merchant
    ADD CONSTRAINT merchant_pkey PRIMARY KEY (id);


--
-- TOC entry 3211 (class 2606 OID 423389)
-- Name: order_detail order_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT order_detail_pkey PRIMARY KEY (id);


--
-- TOC entry 3213 (class 2606 OID 423391)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 3215 (class 2606 OID 423393)
-- Name: otp otp_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.otp
    ADD CONSTRAINT otp_pkey PRIMARY KEY (id);


--
-- TOC entry 3217 (class 2606 OID 423395)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 3219 (class 2606 OID 423397)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 3221 (class 2606 OID 423399)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 3223 (class 2606 OID 423401)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3226 (class 2606 OID 423402)
-- Name: orders fk32ql8ubntj5uh44ph9659tiih; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3224 (class 2606 OID 423407)
-- Name: order_detail fkb8bg2bkty0oksa3wiq5mp5qnc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fkb8bg2bkty0oksa3wiq5mp5qnc FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 3228 (class 2606 OID 423412)
-- Name: user_roles fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 3229 (class 2606 OID 423417)
-- Name: user_roles fkhfh9dx7w3ubf1co1vdev94g3f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3227 (class 2606 OID 423422)
-- Name: product fkk47qmalv2pg906heielteubu7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fkk47qmalv2pg906heielteubu7 FOREIGN KEY (merchant_id) REFERENCES public.merchant(id);


--
-- TOC entry 3225 (class 2606 OID 423427)
-- Name: order_detail fkrws2q0si6oyd6il8gqe2aennc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fkrws2q0si6oyd6il8gqe2aennc FOREIGN KEY (order_id) REFERENCES public.orders(id);


-- Completed on 2024-08-04 22:09:51

--
-- PostgreSQL database dump complete
--

