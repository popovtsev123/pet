DO $$
BEGIN
    IF NOT EXIST (SELECT FROM pg_database WHERE datname = 'candidate') THEN
        CREATE DATABASE candidate;
END IF;
END $$;